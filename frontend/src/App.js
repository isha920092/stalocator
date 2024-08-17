import React, { Component } from 'react';
import { BrowserRouter, Route, Routes } from 'react-router-dom';
import './App.css';
import About from './components/About';
import './components/AboutCss.css';
import AboutText from './components/AboutText';
import BookNow from './components/BookNow';
import DataTable from './components/DataTable';
import FAQ from './components/FAQ';
import Footer from './components/Footer';
import Header from './components/Header';
import Home from './components/Home';
import LoginForm from './components/LoginForm';
import RegistrationForm from './components/RegistrationForm';
import Search from './components/Search';
import Support from './components/Support';
import AdminPage from './components/AdminPage';
import OwnerPage from './components/OwnerPage';
import PastBookings from './components/PastBookings';
import HostelBookings from './components/HostelBookings';
class App extends Component {
  render() {
    return (
      <BrowserRouter>
        <div className='logo'></div>
        <div className="header-container"><Header /></div>
        <div><Routes><Route path="/allbookings" element={<DataTable />} /></Routes></div>
        <div><Routes><Route path="/pastbookings" element={<PastBookings />} /></Routes></div>
        <div><Routes><Route path="hostelbookings/:hostelId" element={<HostelBookings />} /></Routes></div>
        <div className='search'><Routes>
          <Route path="/search" element={<Search />} /></Routes></div>
          <div className='admin'><Routes>
          <Route path="/admin" element={<AdminPage />} /></Routes></div>
          <div className='owner'><Routes>
          <Route path="/owners" element={<OwnerPage />} /></Routes></div>
          {/* <div className='allbookings'><Routes>
          <Route path="/allbookings" element={<DataTable />} /></Routes></div> */}
          
        <div className="app-container">
          <Routes>
            <Route path="/" element={
                <>
                <BookNow />
                <Home />
              </>
            } />
            <Route path="/register" element={
              <>
                <div className='divr1'></div>
                <RegistrationForm />
              </>
            } />
            <Route path="/login" element={
              <>
                <div className='div1'></div>
                <LoginForm />
              </>
            } />
            <Route path="/about" element={<About />} />
            <Route path="/Support" element={<Support />} />
            <Route path="/faq" element={
              <>
                <div className="faq"><FAQ /></div>
              </>
            } />
          </Routes>
        </div>
        <Routes>
          <Route path="/" element={
            <>
              <div className='pgtext'><AboutText /></div>
              <div className="pgimg"></div>
            </>
          } />
        </Routes>
        <div className='temp'></div>
        <hr style={{
          width: '80%',
          height: '2px',
          backgroundColor: 'black'
        }}></hr>
        <div className='footer-container'><Footer /></div>
      </BrowserRouter>
    );
  }
}

export default App;