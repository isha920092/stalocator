import React, { useState, useEffect } from 'react';
import axios from 'axios'; // Import axios for making HTTP requests
import './datatableCss.css';

const DataTable = () => {
    const [data, setData] = useState([]);
    const [loading, setLoading] = useState(true); // Track loading state
    const [error, setError] = useState(null); // Track errors
    const custid = sessionStorage.getItem('id');
    const token = sessionStorage.getItem('token');

    useEffect(() => {
        // Fetch data when the component mounts
        const fetchData = async () => {
            try {
                const response = await axios.get(`http://localhost:8080/customers/reservations/${custid}`, {
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

    const handleDelete = async (id) => {
        try {
            // Make an API call to delete the reservation
            await axios.delete(`http://localhost:8080/customers/reservations/${id}`, {
                headers: {
                    Authorization: `Bearer ${token}` // Attach the token in the Authorization header
                },
                withCredentials: true
            });
            // Remove the deleted item from the state
            setData(data.filter(item => item.id !== id));
        } catch (err) {
            setError('Failed to delete item'); // Handle errors
            console.error(err);
        }
    };

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
                    <th>Hotel Name</th>
                    <th>Check-in Date:</th>
                    <th>Check-out Date</th>
                </tr>
            </thead>
            <tbody>
                {data.length > 0 ? (
                    data.map((item) => (
                        <tr key={item.id}>
                            <td>{item.id}</td>
                            <td>{item.hotelName}</td>
                            <td>{item.checkinDate}</td>
                            <td>{item.checkoutDate}</td>
                            <td>
                                {/* <button className="update-btn" onClick={() => handleUpdate(item.id)}>Update</button> */}
                                <button className="delete-btn" onClick={() => handleDelete(item.id)}>Delete</button>
                                {/* <button className="save-btn" onClick={() => handleSave(item.id)}>Save</button> */}
                            </td>
                        </tr>
                    ))
                ) : (
                    <tr>
                        <td colSpan="3">No data available</td>
                    </tr>
                )}
            </tbody>
        </table>
    );
};

export default DataTable;
