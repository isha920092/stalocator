
import axios from 'axios';
import React, { useState } from 'react';
import './loginCss.css';
import { Navigate,useNavigate } from 'react-router-dom';

const LoginForm = () => {
  const navigate = useNavigate();
  const [email, setEmail] = useState('');
  const [pwd, setPassword] = useState('');
  const [loginError, setLoginError] = useState(null);
  const [loginSuccess, setLoginSuccess] = useState(false);
  const token = sessionStorage.getItem('token');
  //const role = sessionStorage.getItem('role');
  if (token) {
         return <Navigate to="/" />;
     }
  const handleSubmit = async (event) => {
    event.preventDefault();
    try {
      const response = await axios.post('http://localhost:8080/login', { email, pwd });
      const token = response.data.token;
      sessionStorage.setItem('token', token);
      sessionStorage.setItem('id', response.data.user.id);
      sessionStorage.setItem('role', response.data.user.role);
      if (response.status === 200) {
        if (sessionStorage.getItem('role') === 'ADMIN') {
          navigate('/admin');
        } else if (sessionStorage.getItem('role') === 'OWNER') {
          navigate('/owners');
        } else if (sessionStorage.getItem('role') === 'CUSTOMER') {
          navigate('/search');
        }
        setLoginSuccess(true);
        setLoginError(null);
      } else {
        setLoginError(response.data.message);
      }
    } catch (error) {
      if (error.response) {
        setLoginError(error.response.data.message);
      } else {
        setLoginError(error.message);


      }
    }
  };

  return (
    <div className="login-form">
      <h2>Login</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Email:
          <input type="email" value={email} onChange={(event) => setEmail(event.target.value)} placeholder="Email" />
        </label>
        <br />
        <label>
          Password:
          <input
            type="password"
            value={pwd}
            onChange={(event) => setPassword(event.target.value)}
            placeholder="Password"
          />
        </label>
        <br />
        <p>
          Don't have an account? <a href="/register">Register here</a>
        </p>
        {loginError && (
          <p style={{ color: 'red' }}>{loginError}</p>
        )}
        {loginSuccess && (
          <p style={{ color: 'green' }}>Login successful! You will be redirected to the dashboard.</p>
        )}
        <button type="submit">Login</button>

      </form>
    </div>
  );
};
export default LoginForm;