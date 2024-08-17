import axios from 'axios';
import React, { useEffect, useState } from 'react';
import './AdminPageCss.css';
import { Navigate,useNavigate } from 'react-router-dom';

const AdminPage = () => {
  const [owners, setOwners] = useState([]);
  const [customers, setCustomers] = useState([]);
  const [showOwners, setShowOwners] = useState(false); // Default to show pending owners on load
  const [showCustomers, setShowCustomers] = useState(false);
  const [error, setError] = useState(null);
  const role = sessionStorage.getItem('role');
  //const token = sessionStorage.getItem('token');
  if (role !== 'ADMIN') {
    return <Navigate to="/login" />;
  }
  // Load pending owners on component mount
  // useEffect(() => {
  //   axios.get('http://localhost:8080/admin')
  //     .then(response => {
  //       const pendingOwners = response.data.filter(owner => !owner.approved);
  //       setOwners(pendingOwners);
  //     })
  //     .catch(error => {
  //       setError(error.message);
  //     });
  // }, []);

  const handleGetAllOwners = () => {
    axios.get('http://localhost:8080/admin/owners')
      .then(response => {
        setOwners(response.data);
        setShowOwners(true);
        setShowCustomers(false);
      })
      .catch(error => {
        setError(error.message);
      });
  };

  const handleGetAllCustomers = () => {
    axios.get('http://localhost:8080/admin/customers')
      .then(response => {
        setCustomers(response.data);
        setShowCustomers(true);
        setShowOwners(false);
      })
      .catch(error => {
        setError(error.message);
      });
  };

  const handleApproveOwner = (id) => {
    axios.get(`http://localhost:8080/admin/${id}`)
      .then(response => {
        const updatedOwners = owners.map((owner) => {
          if (owner.id === id) {
            return { ...owner, isApproved: true };
          }
          return owner;
        });
        setOwners(updatedOwners);
      })
      .catch(error => {
        setError(error.message);
      });
  };

  const handleDeleteOwner = (id) => {
    axios.delete(`http://localhost:8080/admin/owners/${id}`)
      .then(response => {
        const updatedOwners = owners.filter((owner) => owner.id !== id);
        setOwners(updatedOwners);
      })
      .catch(error => {
        setError(error.message);
      });
  };

  const handleDeleteCustomer = (id) => {
    axios.delete(`http://localhost:8080/admin/customers/${id}`)
      .then(response => {
        const updatedCustomers = customers.filter((customer) => customer.id !== id);
        setCustomers(updatedCustomers);
      })
      .catch(error => {
        setError(error.message);
      });
  };

  return (
    <div className='adminpage'>
      <button onClick={handleGetAllOwners} className='getall'>Owners</button>
      <button onClick={handleGetAllCustomers} className='getall'>Customers</button>

      {showOwners && (
        <div>
          <h2>{owners.length ? 'Owners' : 'No Owners Found'}</h2>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {owners.map((owner) => (
                <tr key={owner.id}>
                  <td>{owner.id}</td>
                  <td>{owner.firstName} {owner.lastName}</td>
                  <td>
                    {!owner.approved ? (
                      <button className="approve-button" onClick={() => handleApproveOwner(owner.id)}>Approve</button>
                    ) : (
                      <button disabled className="approved-button">Approved</button>
                    )}
                    <button className="delete-button" onClick={() => handleDeleteOwner(owner.id)}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {showCustomers && (
        <div>
          <h2>{customers.length ? 'Customers' : 'No Customers Found'}</h2>
          <table>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Actions</th>
              </tr>
            </thead>
            <tbody>
              {customers.map((customer) => (
                <tr key={customer.id}>
                  <td>{customer.id}</td>
                  <td>{customer.firstName} {customer.lastName}</td>
                  <td>
                    <button className="delete-button" onClick={() => handleDeleteCustomer(customer.id)}>Delete</button>
                  </td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}

      {error && (
        <div style={{ color: 'red' }}>
          Error: {error}
        </div>
      )}
    </div>
  );
};

export default AdminPage;
