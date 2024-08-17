import React from 'react';
import './FAQCss.css';

function FAQ() {
  const faqs = [
      {
          question: 'How does Stallocator work?',
          answer: 'Stallocator functions as a comprehensive platform to help users search for and book accommodations. Users enter their destination, travel dates, and other preferences into the system. Stallocator then retrieves and displays a list of available options from various sources.',
      },
      {
          question: 'Are the prices shown on the Stallocator platform final, or are there additional fees?',
          answer: 'The prices displayed on the Stallocator platform typically include the base rate for the accommodation. However, additional fees such as taxes, service charges, or booking fees may apply. These additional costs are usually shown during the booking process before the final payment is made. It’s always a good idea to review the total price breakdown, including any extra fees, before completing the booking.',
      },
      {
          question: 'Can I cancel or modify my booking through Stallocator?',
          answer: 'Cancellation and modification policies depend on the specific accommodation provider and the terms agreed upon during booking. Many Stallocator platforms offer options to cancel or modify bookings, but the availability and terms of these changes vary. You should check the cancellation policy provided by the accommodation at the time of booking. If you need to make changes or cancel your reservation, you can usually do so through your user account on the platform or by contacting customer support, but be aware of any potential penalties or deadlines associated with changes or cancellations.',
      }
  ];  

  return (
    <div className="faq">
      {faqs.map((faq, index) => (
        <div key={index} className="faq-item">
          <h5>
            <button
              className="faq-toggle"
              onClick={() => {
                const element = document.getElementById(`faq-answer-${index}`);
                element.style.display = element.style.display === 'block' ? 'none' : 'block';
              }}
            >
              {faq.question}
            </button>
          </h5>
          <div id={`faq-answer-${index}`} style={{ display: 'none' }} className="faq-answer">
            <p>{faq.answer}</p>
          </div>
        </div>
      ))}
    </div>
  );
}

export default FAQ;