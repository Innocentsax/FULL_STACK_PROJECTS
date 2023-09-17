import React from "react";
import "../styles/Components/_category_size.scss";

function CategorySize({ click }) {
  return (
    <>
      <div className="categorySizeContainer">
        <div className="CategorySize_section1">
          <div className="categorySize XSS" onClick={() => click("XXS")}>
            <span>XXS</span>
          </div>
          <div className="categorySize XS" onClick={() => click("XS")}>
            <span>XS</span>
          </div>
          <div className="categorySize S" onClick={() => click("S")}>
            <span>S</span>
          </div>
          <div className="categorySize M" onClick={() => click("M")}>
            <span>M</span>
          </div>
        </div>
        <div className="CategorySize_section2">
          <div className="categorySize XSS" onClick={() => click("L")}>
            <span>L</span>
          </div>
          <div className="categorySize XS" onClick={() => click("XL")}>
            <span>XL</span>
          </div>
          <div className="categorySize S" onClick={() => click("23")}>
            <span>23</span>
          </div>
          <div className="categorySize M" onClick={() => click("24")}>
            <span>24</span>
          </div>
        </div>
        <div className="CategorySize_section3">
          <div className="categorySize XSS" onClick={() => click("25")}>
            <span>25</span>
          </div>
          <div className="categorySize XS" onClick={() => click("26")}>
            <span>26</span>
          </div>
          <div className="categorySize S" onClick={() => click("27")}>
            <span>27</span>
          </div>
          <div className="categorySize M" onClick={() => click("28")}>
            <span>28</span>
          </div>
        </div>
        <div className="CategorySize_section4">
          <div className="categorySize XSS" onClick={() => click("29")}>
            <span>29</span>
          </div>
          <div className="categorySize XS" onClick={() => click("30")}>
            <span>30</span>
          </div>
          <div className="categorySize S" onClick={() => click("31")}>
            <span>31</span>
          </div>
          <div className="categorySize M" onClick={() => click("XS")}>
            <span>32</span>
          </div>
        </div>
      </div>
    </>
  );
}

export default CategorySize;
