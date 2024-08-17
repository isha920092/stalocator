import React from 'react';
import { useNavigate } from 'react-router-dom';
import './HeaderCss.css';

const Header = () => {
  const navigate = useNavigate();
  const token = sessionStorage.getItem('token');

  const handleLogout = () => {
    if (confirm("Are you sure to Logout?")) {
    sessionStorage.removeItem('token');
    sessionStorage.removeItem('role');
    sessionStorage.removeItem('id');
    navigate('/'); 
    }
  };

  return (
    <header>  
      <nav className="nav-right">
        <ul>
          <li><a href="/">HOME</a></li>
          <li><a href="/register">ACCOUNT</a></li>
          <li><a href="/about">ABOUT US</a></li>
          <li><a href="/faq">FAQ</a></li>
          <li><a href="/Support" target="_blank">SUPPORT</a></li>  
          {token ? (
            <li><a href="/" onClick={handleLogout}>LOGOUT</a></li>
          ) : (
            <li><a href="/login">LOGIN</a></li>
          )}
        </ul>
      </nav>
    </header>
  );
};

export default Header;
