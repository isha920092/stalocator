import React, { useState, useEffect } from 'react';
import axios from 'axios'; // Import axios for making HTTP requests
import './datatableCss.css';
import { Navigate,useNavigate } from 'react-router-dom';

const PastBookings = () => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true); // Track loading state
    const [error, setError] = useState(null); // Track errors
    const custid = sessionStorage.getItem('id');
    const token = sessionStorage.getItem('token');
    const role = sessionStorage.getItem('role');
    const navigate = useNavigate();
    if (!token||role!=='CUSTOMER') {
           return <Navigate to="/login" />;
       }
    useEffect(() => {
        // Fetch data when the component mounts
        const fetchData = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/customers/reservations/history/${custid}`, {
                    headers: {
                        Authorization: `Bearer ${token}` // Attach the token in the Authorization header
                    },
                    withCredentials: true
                });
                setData(response.data); // Update the state with fetched data
            } catch (err) {
                setError('Failed to fetch data'); // Handle errors
                console.error(err);
            } finally {
                setLoading(false); // Set loading to false after the fetch is complete
            }
        };

        fetchData(); // Call the function to fetch data
    }, [custid, token]); // Depend on customerId and token

   

    const handleUpdate = (id) => {
        console.log(`Update button clicked for id ${id}`);
        // TO DO: implement update logic
    };

    const handleSave = (id) => {
        console.log(`Save button clicked for id ${id}`);
        // TO DO: implement save logic
    };

    if (loading) {
        return <p>Loading...</p>; // Show loading state
    }

    if (error) {
        return <p>{error}</p>; // Show error message
    }

    return (
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Hotel Name</th>
                    <th>Type</th>
                    <th>No. of beds</th>
                    <th>Check-in Date:</th>
                    <th>Check-out Date</th>
                </tr>
            </thead>
            <tbody>
                {data.length > 0 ? (
                    data.map((item) => (
                        <tr key={item.id}>
                            <td>{item.id}</td>
                            <td>{item.hostelname}</td>
                            <td>{item.typeDorm}</td>
                            <td>{item.beds}</td>
                            <td>{item.bookingStartDate}</td>
                            <td>{item.bookingEndDate}</td>
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td colSpan="3">No past bookings</td>
                    </tr>
                )}
            </tbody>
        </table>
    );
};

export default PastBookings;
