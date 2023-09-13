// import React, { useState } from 'react';
// import './Modal.css';

// function Modal(props) {
//   const [isOpen, setIsOpen] = useState(false);

//   const toggleModal = () => {
//     setIsOpen(!isOpen);
//   };

//   return (
//     <>
//       <button onClick={toggleModal}>{props.buttonText}</button>
//       {isOpen && (
//         <div className="modal">
//           <div className="modal-content">
//             <span className="modal-close" onClick={toggleModal}>
//               &times;
//             </span>
//             {props.children}
//           </div>
//         </div>
//       )}
//     </>
//   );
// }

// export default Modal;





import React, { useState } from 'react';
import './Modal.css';

function Modal(props) {
  const [isOpen, setIsOpen] = useState(false);

  const toggleModal = () => {
    setIsOpen(!isOpen);
  };

  return (
    <>
      <button onClick={toggleModal}>{props.buttonText}</button>
      {isOpen && (
        <div className="modal">
          <div className="modal-content">
            <span className="modal-close" onClick={toggleModal}>
              close
            </span>
            {props.children}
          </div>
        </div>
      )}
    </>
  );
}

export default Modal;
