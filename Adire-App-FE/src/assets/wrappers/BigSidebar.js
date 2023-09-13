import styled from 'styled-components'

const Wrapper = styled.aside`
  p {
    margin-bottom: 0;
  }
  display: none;
  @media (min-width: 992px) {
    display: block;
    // box-shadow: 1px 0px 0px 0px rgba(0, 0, 0, 0.1);
    .sidebar-container {
      background: var(--white);
      min-height: 100vh;
      height: 100%;
      width: 230px;
      margin-left: -230px;
      transition: var(--transition);
    }
    .content {
      position: sticky;
      top: 0;
    }

    .show-sidebar {
      margin-left: 0;
    }

    header {
      height: 4rem;
      display: flex;
      align-items: center;
      padding-left: 2.5rem;
    }
    .nav-links {
      /* padding-top: 2rem; */
      display: flex;
      flex-direction: column;
      padding : 1rem;
    }
    .nav-link {
      display: flex;
      align-items: center;
      color: var(--grey-500);
      padding: .85rem 0;
      padding-left: 2rem;
      margin-bottom : 1rem;
      text-transform: capitalize;
      transition: var(--transition);
    }

    .sub-heading {
      padding-left: 2rem;
      margin-bottom : 1rem;
    }
    // .nav-link:hover {
    //   color: var(--grey-900);
    // }
    // .nav-link:hover .icon {
    //   color: var(--primary-500);
    // }
    .icon {
        stroke : #5925DC;
      font-size: 1.5rem;
      margin-right: 1rem;
      display: grid;
      place-items: center;
      transition: var(--transition);
    }

    .logout-btn {
      margin-top: 1rem;
    }

    .active {
      background : #5925DC;
      border-radius : 0.5rem;
      color: #fff;
    }
    .active .icon {
        stroke : #fff;
      color: var(--primary-500);
    }
    .other-nav-links{
        padding: 0;
        /* margin-top : .2rem; */
    }
    .other-active {
        color: #5925DC;
    }
  }
`
export default Wrapper