import React from 'react'
import { Link } from "react-router-dom";


const NotFoundPage = () => {
  return (
    <div className="row mt-5">
      <div className="col-md-12 text-center">
        <span className="display-1">404</span>
        <div className="mb-4 lead">
          Oops! We can't seem to find the page you are looking for
        </div>
        <Link to="/" className="btn btn-link">
          Back to Home
        </Link>
      </div>
    </div>
  );
}

export default NotFoundPage