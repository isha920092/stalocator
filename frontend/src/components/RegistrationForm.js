import React, { useState } from 'react';
import axios from 'axios'; // Import axios
import { useNavigate ,Navigate} from 'react-router-dom'; // Import navigate function
import './RegistrationCss.css';

const RegistrationForm = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [role, setRole] = useState('');
  const [registrationError, setRegistrationError] = useState(null);
  const [fieldErrors, setFieldErrors] = useState({}); // Added state for field errors

  const navigate = useNavigate(); // Initialize navigate
  const token = sessionStorage.getItem('token');
  //const role = sessionStorage.getItem('role');
  if (token) {
         return <Navigate to="/" />;
     }
  const handleSubmit = (event) => {
    event.preventDefault();
    
    // Clear previous errors
    setFieldErrors({});
    setRegistrationError(null);
    
    // Validation checks
    let errors = {};
    if (!firstName) errors.firstName = "First name is required";
    if (!lastName) errors.lastName = "Last name is required";
    if (!email) errors.email = "Email is required";
    if (!password) errors.password = "Password is required";
    if (password !== confirmPassword) errors.passwordMatch = "Passwords do not match";
    if (!role) errors.role = "Please select a role";

    // If there are errors, update the state and return
    if (Object.keys(errors).length > 0) {
      setFieldErrors(errors);
      return;
    }

    let endpoint = '';
  if (role === 'CUSTOMER') {
    endpoint = 'http://localhost:8080/customers';
  } else if (role === 'OWNER') {
    endpoint = 'http://localhost:8080/owner';
  }

  axios.post(endpoint, { firstName, lastName, email, password })
      .then((response) => {
        if (response.status === 200) {
          console.log(response.data);
          setFirstName('');
          setLastName('');
          setEmail('');
          setPassword('');
          setConfirmPassword('');
          setRole('');
          //navigate('/login'); // redirect to login page
        } else {
          console.log(response.data);
          setRegistrationError(response.data.message);
          setFirstName('');
          setLastName('');
          setEmail('');
          setPassword('');
          setConfirmPassword('');
          setRole('');
        }
      })
      .catch((error) => {
        if (error.response) {
          setRegistrationError(error.response.data.message);
        } else {
          setRegistrationError(error.message);
        }
      });
  };

  const handleRoleChange = (event) => {
    setRole(event.target.value);
    setFieldErrors((prev) => ({ ...prev, role: '' })); // Clear role error when user selects a role
  };

  return (
    <div className='outer'>
      <div className='main'>
        <div className='img'></div>
      </div>
      <div className="registration-form">
        <h2>Register</h2>
        <form onSubmit={handleSubmit}>
          <label>
            First Name:
            <input
              type="text"
              value={firstName}
              onChange={(event) => setFirstName(event.target.value)}
              placeholder="First Name"
            />
            {fieldErrors.firstName && <div style={{ color: 'red' }}>{fieldErrors.firstName}</div>}
          </label>
          <br />
          <label>
            Last Name:
            <input
              type="text"
              value={lastName}
              onChange={(event) => setLastName(event.target.value)}
              placeholder="Last Name"
            />
            {fieldErrors.lastName && <div style={{ color: 'red' }}>{fieldErrors.lastName}</div>}
          </label>
          <br />
          <label>
            Email:
            <input
              type="email"
              value={email}
              onChange={(event) => setEmail(event.target.value)}
              placeholder="Email"
            />
            {fieldErrors.email && <div style={{ color: 'red' }}>{fieldErrors.email}</div>}
          </label>
          <br />
          <label>
            Password:
            <input
              type="password"
              value={password}
              onChange={(event) => setPassword(event.target.value)}
              placeholder="Password"
            />
            {fieldErrors.password && <div style={{ color: 'red' }}>{fieldErrors.password}</div>}
          </label>
          <br />
          <label>
            Confirm Password:
            <input
              type="password"
              value={confirmPassword}
              onChange={(event) => setConfirmPassword(event.target.value)}
              placeholder="Confirm Password"
            />
            {fieldErrors.passwordMatch && <div style={{ color: 'red' }}>{fieldErrors.passwordMatch}</div>}
          </label>
          <br />
          <div className="radio-group">
            <label>Role: </label>
            <input
              type="radio"
              id="user"
              name="role"
              value="CUSTOMER"
              checked={role === 'CUSTOMER'}
              onChange={handleRoleChange}
            />
            <label htmlFor="user">Customer</label>
            <input
              type="radio"
              id="owner"
              name="role"
              value="OWNER"
              checked={role === 'OWNER'}
              onChange={handleRoleChange}
            />
            <label htmlFor="owner">Owner</label>
            {fieldErrors.role && <div style={{ color: 'red' }}>{fieldErrors.role}</div>}
          </div>
          <br />
          <button type="submit">Register</button>
          {registrationError && <div style={{ color: 'red' }}>{registrationError}</div>}
          <p>
            Already registered? <a href="/login">Login here</a>
          </p>
        </form>
      </div>
    </div>
  );
};

export default RegistrationForm;
