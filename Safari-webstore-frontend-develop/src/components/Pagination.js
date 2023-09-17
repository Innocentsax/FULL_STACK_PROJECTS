import React from "react";
import { Container } from "semantic-ui-react";


const Pagination = ({ itemsPerPage, totalPages, paginate, currentPage}) => {
    const pageNumbers = [];

    const size = Math.ceil(totalPages / itemsPerPage);

    for (let i = 1; i <= size ; i++) {
        pageNumbers.push(i);
    }

    return (
      <Container textAlign="center">
        <div className="pagination">
            {
                pageNumbers.map(
                    number => <button key={number} href="#" onClick={() => paginate(number)} className={number === currentPage ? "blocks active" : "blocks inactive" } >{number}</button>
                )
            }
        </div>
      </Container>

    )
}

export default Pagination;