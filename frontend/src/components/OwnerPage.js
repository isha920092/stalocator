import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Navigate, useNavigate } from 'react-router-dom';
import './OwnerPage.css';
import image1 from './Hotel.png';

function OwnerPage() {
  const [hostels, setHostels] = useState([]);
  const [newHostel, setNewHostel] = useState({
    name: '',
    description: '',
    address: '',
    facilities: '',
    price: '',
    female_beds: '',
    male_beds: '',
    mixed_beds: '',
  });
  const [error, setError] = useState('');
  const token = sessionStorage.getItem('token');
  const id = sessionStorage.getItem('id');
  const role = sessionStorage.getItem('role');
  const navigate = useNavigate(); // Add useNavigate hook

  if (!token || role !== 'OWNER') {
    return <Navigate to="/login" />;
  }

  useEffect(() => {
    if (id && token) {
      axios.get(`http://localhost:8080/owner/hostels/${id}`, {
        headers: {
          'Content-Type': 'application/json',
          'Authorization': `Bearer ${token}`
        }
      })
      .then(response => {
        setHostels(response.data);
      })
      .catch(error => {
        console.error('Error fetching hostels:', error);
      });
    }
  }, [id, token]);

  const handleInputChange = (event) => {
    const { name, value } = event.target;
    setNewHostel((prevHostel) => ({ ...prevHostel, [name]: value }));
  };

  const handleSubmitNewHostel = (event) => {
    event.preventDefault();
    
    // Validation
    const { name, address, price, female_beds, male_beds, mixed_beds } = newHostel;
    const totalBeds = parseInt(female_beds || '0') + parseInt(male_beds || '0') + parseInt(mixed_beds || '0');
    
    if (!name || !address || !price) {
      setError('Name, address, and price are required.');
      return;
    }
    
    if (totalBeds < 2) {
      setError('At least 2 beds must be specified in total.');
      return;
    }
    
    const hostelData = {
      ...newHostel,
      female_beds: parseInt(female_beds) || 0,
      male_beds: parseInt(male_beds) || 0,
      mixed_beds: parseInt(mixed_beds) || 0,
    };
    
    setError(''); // Clear any previous error
    
    axios.post(`http://localhost:8080/owner/hostels/${id}`, hostelData, {
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    })
    .then(response => {
      setHostels([...hostels, response.data]);
      setNewHostel({
        name: '',
        description: '',
        address: '',
        facilities: '',
        price: '',
        female_beds: '',
        male_beds: '',
        mixed_beds: '',
      }); // Clear the form
    })
    .catch(error => {
      console.error('Error adding hostel:', error);
    });
  };

  const handleHostelClick = (hostelId) => {
    navigate(`/hostelbookings/${hostelId}`); // Redirect to hostel bookings page
  };

  return (
    <>
      <form onSubmit={handleSubmitNewHostel} className='ipform'>
        <table>
          <tbody>
            <tr>
              <td>
                <label htmlFor="name">Hostel Name:</label>
                <input
                  type="text"
                  id="name"
                  name="name"
                  value={newHostel.name}
                  onChange={handleInputChange}
                />
              </td>
              <td>
                <label htmlFor="description">Description:</label>
                <input
                  type="text"
                  id="description"
                  name="description"
                  value={newHostel.description}
                  onChange={handleInputChange}
                />
              </td>
              <td>
                <label htmlFor="address">City:</label>
                <input
                  type="text"
                  id="address"
                  name="address"
                  value={newHostel.address}
                  onChange={handleInputChange}
                />
              </td>
              <td>
                <label htmlFor="facilities">Facilities:</label>
                <input
                  type="text"
                  id="facilities"
                  name="facilities"
                  value={newHostel.facilities}
                  onChange={handleInputChange}
                />
              </td>
              </tr>
              <tr>
              <td>
                <label htmlFor="price">Price:</label>
                <input
                  type="number"
                  id="price"
                  name="price"
                  value={newHostel.price}
                  onChange={handleInputChange}
                />
              </td>
              <td>
                <label htmlFor="female_beds">Female Beds:</label>
                <input
                  type="number"
                  id="female_beds"
                  name="female_beds"
                  value={newHostel.female_beds}
                  onChange={handleInputChange}
                />
              </td>
              <td>
                <label htmlFor="male_beds">Male Beds:</label>
                <input
                  type="number"
                  id="male_beds"
                  name="male_beds"
                  value={newHostel.male_beds}
                  onChange={handleInputChange}
                />
              </td>
              <td>
                <label htmlFor="mixed_beds">Mixed Beds:</label>
                <input
                  type="number"
                  id="mixed_beds"
                  name="mixed_beds"
                  value={newHostel.mixed_beds}
                  onChange={handleInputChange}
                />
              </td>
            </tr>
          </tbody>
        </table>
        {error && <p className="error">{error}</p>}
        <button type="submit" id='addhotel'>
          Add Hostel
        </button>
      </form>

      <div className="owner-page">
        <div className="hostel-grid">
          {hostels.length > 0 ? (
            <div className="search-results">
              {hostels.map((hostel) => (
                <div
                  key={hostel.id}
                  className="search-result"
                  // onClick={() => handleHostelClick(hostel.id)} // Add click handler
                >
                  <img src={image1} alt={hostel.name} /> {/* Use fallback image */}
                  <div className="result-info">
                    <h3>{hostel.name}</h3>
                    <p>Description: {hostel.description || 'No description available'}</p>
                    <p>Address: {hostel.address}</p>
                    <p>Facilities: {hostel.facilities}</p>
                    <p>Price: Rs{hostel.price}</p>
                    <p>Female Beds: {hostel.female_beds || 'N/A'}</p>
                    <p>Male Beds: {hostel.male_beds || 'N/A'}</p>
                    <p>Mixed Beds: {hostel.mixed_beds || 'N/A'}</p>
                  </div>
                </div>
              ))}
            </div>
          ) : (
            <p>No hostels found.</p>
          )}
        </div>
      </div>
    </>
  );
}

export default OwnerPage;
