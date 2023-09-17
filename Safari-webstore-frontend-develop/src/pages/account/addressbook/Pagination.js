import React from 'react';

const Pagination = ({ itemsPerPage, totalPages, paginate, currentPage}) => {
    const pageNumbers = [];

    const size = Math.ceil(totalPages / itemsPerPage);

    for (let i = 1; i <= size ; i++) {
        pageNumbers.push(i);
    }

    return (
        <div className="pagination">
            {
                pageNumbers.map(
                    number => <button key={number} href="#" onClick={() => paginate(number)} className={number === currentPage ? "active" : "inactive" } >{number}</button>
                )
            }
        </div>
    )
}

export default Pagination;