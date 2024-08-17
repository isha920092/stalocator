
import React from 'react';
import './booknowCss.css';
function BookNow() {
  return (
    <button className="book-now-button">
        <a href="/search" >BOOK NOW</a>
      <span className="exclusive-rates">Exclusive Rates</span>
    </button>
  );
}

export default BookNow;