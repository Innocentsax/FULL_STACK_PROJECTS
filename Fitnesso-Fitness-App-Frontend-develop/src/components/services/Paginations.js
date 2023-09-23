import React, { useState, useEffect } from "react";
import './Pagination.css';

const LEFT_PAGE = "LEFT";
const RIGHT_PAGE = "RIGHT";

const range = (from, to, step = 1) => {
  let i = from;
  const range = [];

  while (i <= to) {
    range.push(i);
    i += step;
  }

  console.log(range);
  return range;
};

const Paginations = (props) => {
  const {
    totalRecords,
    pageLimit,
    pageNeighbours,
    onPageChanged,
    currentPage
  } = props;
  const [totalPages, setTotalPages] = useState(0);
  useEffect(() => {
    setTotalPages(Math.ceil(totalRecords / pageLimit));
  }, [setTotalPages]);

  const fetchPageNumbers = () => {
    const totalNumbers = pageNeighbours * 2 + 3;
    const totalBlocks = totalNumbers + 2;

    if (totalPages > totalBlocks) {
      const startPage = Math.max(2, currentPage - pageNeighbours);
      const endPage = Math.min(totalPages - 1, currentPage + pageNeighbours);

      let pages = range(startPage, endPage);

      const hasLeftSpill = startPage > 2;
      const hasRightSpill = totalPages - endPage > 1;
      const spillOffset = totalNumbers - (pages.length + 1);

      switch (true) {
        case hasLeftSpill && !hasRightSpill: {
          const extraPages = range(startPage - spillOffset, startPage - 1);
          pages = [LEFT_PAGE, ...extraPages, ...pages];
          break;
        }
        case hasLeftSpill && hasRightSpill:
        default: {
          pages = [LEFT_PAGE, ...pages, RIGHT_PAGE];
          break;
        }
      }
      return [1, ...pages, totalPages];
    }
    return range(1, totalPages);
  };

  const pages = fetchPageNumbers() || [];
  console.log("The pages are: " + pages);
  return (
    <nav aria-label="Countries Pagination">
      <ul className="product-pagination">
        {pages.map((page, index) => {
          if (page === LEFT_PAGE)
            return (
              <li key={index} className="page-item">
                <a
                  href="/"
                  className="page-link"
                  aria-label="Previous"
                  onClick={(e) => onPageChanged(e, currentPage - 5)}
                >
                  <span aria-hidden="true">&laquo;</span>
                </a>
              </li>
            );

          if (page === RIGHT_PAGE)
            return (
              <li key={index} className="page-item">
                <a
                  className="page-link"
                  href="/"
                  aria-label="Next"
                  onClick={(e) => onPageChanged(e, currentPage + 3)}
                >
                  <span aria-hidden="true">&raquo;</span>
                </a>
              </li>
            );

          return (
            <li
              key={index}
              className={`page-item${currentPage === page ? " active" : ""}`}
            >
              <a
                className="page-link"
                href="/"
                onClick={(e) => onPageChanged(e, page)}
              >
                {page}
              </a>
            </li>
          );
        })}
      </ul>
    </nav>
  );
};

export default Paginations;