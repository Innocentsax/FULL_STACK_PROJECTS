import styled from 'styled-components'

const Wrapper = styled.main`
  nav {
    background-color: var(--white);
    /* width: var(--fluid-width);
    max-width: var(--max-width);
    margin: 0 auto; */
    height: var(--nav-height);
    display: flex;
    align-items: center;
    justify-content: center;
  }
  .nav-center {
    display: flex;
    width: 90vw;
    align-items: center;
    justify-content: space-between;
  }
  .nav-btn {
    display: inline-block;
    margin-right: 1.5rem;
    text-transform: capitalize;
    background: transparent;
    border: transparent;
    color: var(--primary-500);
    cursor: pointer;
    letter-spacing: var(--letterSpacing);
  }

  .about-btn {
    padding-block: 0.5rem;
    padding-right: 1rem;
    border-right: 1px solid var(--grey-400);
  }

  .background {
    position: absolute;
    top: -10vmin;
    right: -20vmin;
    width: 100vmin;
    height: 100vmin;
    /* border-radius: 47% 53% 91% 39% / 45% 91% 49% 55%; */
    /* background: #9B8AFB; */
    z-index: -3;
  }
  .landing-section {
    position: relative;
    overflow: hidden;
  }

  .page {
    min-height: calc(90vh - var(--nav-height));
    display: grid;
    align-items: center;
    
  }
  h1 {
    font-weight: 700;
    font-size: 2rem;
    text-transform: none;
    span {
      color: var(--primary-500);
    }
  }
  p {
    color: var(--grey-600);
  }
  .main-img {
    display: none;
  }



/* section */
.section {
  padding: 2rem 0;
}
/* .section-title {
  text-align: center;
  margin-bottom: 4rem;
}
.section-title h2 {
  text-transform: uppercase;
}
.section-title span {
  color: var(--clr-primary-5);
} */
.section-center {
  width: 90vw;
  margin: 0 auto;
  max-width: 1120px;
}

/*
=============== 
Footer
===============
*/


.footer {
  background: var(--primary-50);
  text-align: center;
  padding-left: 2rem;
  padding-right: 2rem;
}
.footer-links,
.footer-icons {
  display: flex;
  justify-content: center;
  gap: 1rem;
  margin-bottom: 1.2rem;
  margin-top: 0rem;
  flex-wrap: wrap;
}
.footer-icons,
.copyright {
  margin: 0;
}
.footer-link {
  color: var(--grey-600);
  text-transform: capitalize;
  font-size: 1rem;
  letter-spacing: var(--spacing);
  transition: var(--transition);
}
.footer-bottom {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-top: 0.7rem;
}
.footer-link:hover {
  color: var(--clr-primary-5);
}
.footer-icon {
  font-size: 0.6rem;
  color: var(--grey-600);
  transition: var(--transition);
  width: 2rem;
  height: 2rem;
  border-radius: 50%;
  background-color: var(--grey-400);
  display: grid;
  place-items: center;
}
.footer-svg {
  font-size: 1rem;
}
.footer-icon:hover {
  color: var(--black);
}

.support {
  margin: 0;
  margin-top: 1rem;
}

@media (min-width: 992px) {
  .page {
    grid-template-columns: 1fr 1fr;
    column-gap: 3rem;
  }
  .main-img {
    display: block;
  }
}


`
export default Wrapper