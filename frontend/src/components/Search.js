import axios from 'axios';
import React, { useState } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import image1 from './Hotel.png'; // Fallback image if needed
import './searchCss.css';

const SearchPage = () => {
    const [city, setCity] = useState('');
    const [checkinDate, setCheckinDate] = useState('');
    const [checkoutDate, setCheckoutDate] = useState('');
    const [numBeds, setNumBeds] = useState(1);
    const [typeDorm, setTypeDorm] = useState(''); // New state for dorm type
    const [searchResults, setSearchResults] = useState([]);
    const [errorMessage, setErrorMessage] = useState(null); // State for error message
    const [sortOrder, setSortOrder] = useState('asc');
    const token = sessionStorage.getItem('token');
    const custid = sessionStorage.getItem('id');
    const role = sessionStorage.getItem('role');
    const navigate = useNavigate();

    if (!token || role !== 'CUSTOMER') {
        return <Navigate to="/login" />;
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        // Clear previous error message
        setErrorMessage(null);

        // Create search DTO
        const searchDTO = {
            address: city,
            bookingStartDate: checkinDate,
            bookingEndDate: checkoutDate,
            beds: numBeds,
            typeDorm: typeDorm || null // Send dorm type if selected
        };

        try {
            // Make a POST request to your API endpoint
            const response = await axios.post(`http://localhost:8080/customers/reservations/search/${custid}`, searchDTO, {
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': `Bearer ${token}`
                }
            });

            // Update search results with API response data
            setSearchResults(response.data);
        } catch (error) {
            console.error('Error fetching search results:', error);

            // Set error message and clear search results
            setErrorMessage(error.response?.data?.message || 'An error occurred');
            setSearchResults([]); // Clear the search results on error
        }
    };

    const handleAllBookings = (event) => {
        event.preventDefault();
        navigate('/allbookings');
    };

    const handlePastBookings = (event) => {
        event.preventDefault();
        navigate('/pastbookings');
    };

    const handleSort = async (event) => {
        event.preventDefault();
        setSortOrder(prevOrder => prevOrder === 'asc' ? 'desc' : 'asc');

        const sortedResults = [...searchResults].sort((a, b) => {
            if (sortOrder === 'asc') {
                return a.price - b.price;
            } else {
                return b.price - a.price;
            }
        });

        setSearchResults(sortedResults);
    };

    const handleBooking = async (result) => {
        const confirmBooking = window.confirm(`Do you want to book ${result.name}?`);
        
        if (confirmBooking) {
            try {
                // Make a POST request to your API endpoint to book the hostel
                await axios.post(`http://localhost:8080/customers/reservations/${custid}`, {
                    bookingStartDate: checkinDate,
                    bookingEndDate: checkoutDate,
                    beds: numBeds,
                    typeDorm: typeDorm,
                    hostelId: result.id
                }, {
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    }
                });

                alert(`You have successfully booked ${result.name}!`);
                // Optionally, you could refresh search results or navigate away
                 //navigate('/search');  //Uncomment if you want to navigate after booking
                 window.location.reload();
            } catch (error) {
                console.error('Error making booking:', error);
                alert('Failed to book the hostel. Please try again.');
            }
        }
    };

    return (
        <div className="search-page">
            <form onSubmit={handleSubmit}>
                <table>
                    <tbody>
                        <tr>
                            <td>
                                <label htmlFor="city">City:</label>
                            </td>
                            <td>
                                <input
                                    type="text"
                                    id="city"
                                    value={city}
                                    onChange={(e) => setCity(e.target.value)}
                                    required
                                />
                            </td>
                            <td>
                                <label htmlFor="checkin">Check-in Date:</label>
                            </td>
                            <td>
                                <input
                                    type="date"
                                    id="checkin"
                                    value={checkinDate}
                                    onChange={(e) => setCheckinDate(e.target.value)}
                                    required
                                />
                            </td>

                            <td>
                                <label htmlFor="checkout">Check-out Date:</label>
                            </td>
                            <td>
                                <input
                                    type="date"
                                    id="checkout"
                                    value={checkoutDate}
                                    onChange={(e) => setCheckoutDate(e.target.value)}
                                    required
                                />
                            </td>

                            <td>
                                <label htmlFor="numBeds">Number of Beds:</label>
                            </td>
                            <td>
                                <select
                                    id="numBeds"
                                    value={numBeds}
                                    onChange={(e) => setNumBeds(parseInt(e.target.value))}
                                >
                                    <option value={1}>1 Bed</option>
                                    <option value={2}>2 Beds</option>
                                    <option value={3}>3 Beds</option>
                                    <option value={4}>4 Beds</option>
                                </select>
                            </td>

                            <td>
                                <label htmlFor="typeDorm">Dorm Type:</label>
                            </td>
                            <td>
                                <select
                                    id="typeDorm"
                                    value={typeDorm}
                                    onChange={(e) => setTypeDorm(e.target.value)}
                                >
                                    <option value="">Any</option>
                                    <option value="MALE">Male</option>
                                    <option value="FEMALE">Female</option>
                                    <option value="MIXED">Mixed</option>
                                </select>
                            </td>
                        </tr>
                    </tbody>
                </table>
                <button type="submit">Search</button>
                <button type="button" className="sortButton" onClick={handleSort}>Sort</button>
                <button type="button" className="allbookings" onClick={handleAllBookings}>All Bookings</button>
                <button type="button" className="pastbookings" onClick={handlePastBookings}>Past Bookings</button>
            </form>

            {/* Display error message if any */}
            {errorMessage && <p style={{ color: 'red' }}>{errorMessage}</p>}

            {searchResults.length > 0 && (
                <div className="search-results">
                    {searchResults.map((result) => (
                        <div
                            key={result.id}
                            className="search-result"
                        >
                            <img src={image1} alt={result.name} /> {/* Use fallback image */}
                            <div className="result-info">
                                <h3>{result.name}</h3>
                                <p>Description: {result.description}</p>
                                <p>Address: {result.address}</p>
                                <p>Facilities: {result.facilities}</p>
                                <p>Price: Rs {result.price}</p>
                                <p>Available Beds: {result.beds_available}</p>
                                <button className="booknow" onClick={() => handleBooking(result)}>Book Now</button>
                            </div>
                        </div>
                    ))}
                </div>
            )}
        </div>
    );
};

export default SearchPage;
