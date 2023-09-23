
import "./css/IconClear.css"
function IconClear(props) {
    return (
      <div className="icon-clear-icon-clear">
        <svg
          width="100%"
          height="100%"
          preserveAspectRatio="none"
          viewBox="0 0 24 24"
          fill="none"
          xmlns="http://www.w3.org/2000/svg"
        >
          <path
            d="M 19 6.41 L 17.59 5 L 12 10.59 L 6.41 5 L 5 6.41 L 10.59 12 L 5 17.59 L 6.41 19 L 12 13.41 L 17.59 19 L 19 17.59 L 13.41 12 L 19 6.41 Z"
            fill="#757575"
           />
        </svg>
      </div>
    );
  }
  
  IconClear.defaultProps = {
    className: "",
  };

export default IconClear;
  
  