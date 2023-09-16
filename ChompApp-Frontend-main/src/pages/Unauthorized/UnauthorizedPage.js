import React from 'react'
import { Link } from "react-router-dom";


const UnauthorizedPage = () => {
  return (
    <div className="row mt-5">
      <div className="col-md-12 text-center">
        <span className="display-1">401</span>
        <div className="mb-4 lead">
          Unauthorized: Access to this resource is denied.
        </div>
        <Link to="/" className="btn btn-link">
          Back to Home
        </Link>
      </div>
    </div>
  );
};

export default UnauthorizedPage;