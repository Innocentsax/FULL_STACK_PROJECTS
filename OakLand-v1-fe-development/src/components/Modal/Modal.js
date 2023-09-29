import React from 'react';

const Modal = ({ children, onClose }) => (
  <div className="fixed top-0 left-0 right-0 bottom-0 z-10 bg-black bg-opacity-50 overflow-auto">
    <div className="relative mx-auto max-w-lg mt-10">
      <div className="bg-white rounded-lg shadow-xl p-10 lg:mt-40 mt-20 h-50 max-h-50">
        <button className="absolute top-3 right-5 mb-5 text-gray-600 hover:text-gray-700 text-3xl" onClick={onClose}>
          &times;
        </button>
        {children}
      </div>
    </div>
  </div>
);

export default Modal;