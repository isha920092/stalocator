import React, { useState } from 'react';
import './SupportCss.css';

const Support = () => {
  const [firstName, setFirstName] = useState('');
  const [lastName, setLastName] = useState('');
  const [email, setEmail] = useState('');
  const [message, setMessage] = useState('');

  const handleSubmit = (event) => {
    event.preventDefault();
    // Send the data to your server here
    console.log('Form submitted:', { firstName, lastName, email, message });
    // Reset the form
    setFirstName('');
    setLastName('');
    setEmail('');
    setMessage('');
  };

  return (
    <div className="support-container">
      <h2>Contact Us</h2>
      <form onSubmit={handleSubmit}>
        <table>
          <tbody>
            <tr>
              <td><label htmlFor="firstName">First Name:</label></td>
              <td><input
                type="text"
                id="firstName"
                value={firstName}
                onChange={(e) => setFirstName(e.target.value)}
                required
              /></td>
            </tr>
            <tr>
              <td><label htmlFor="lastName">Last Name:</label></td>
              <td><input
                type="text"
                id="lastName"
                value={lastName}
                onChange={(e) => setLastName(e.target.value)}
                required
              /></td>
            </tr>
            <tr>
              <td><label htmlFor="email">Email:</label></td>
              <td><input
                type="email"
                id="email"
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                required
              /></td>
            </tr>
            <tr>
              <td><label htmlFor="message">Message:</label></td>
              <td><textarea
                id="message"
                value={message}
                onChange={(e) => setMessage(e.target.value)}
                required
              /></td>
            </tr>
          </tbody>
        </table>
        <button type="submit">Submit</button>
      </form>
    </div>
  );
};

export default Support;
