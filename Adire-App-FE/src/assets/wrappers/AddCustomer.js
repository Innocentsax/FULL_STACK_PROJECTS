import styled from 'styled-components'

const Wrapper = styled.div`
  .page-layout {
    display: grid;
    grid-template-columns: 1fr;
    column-gap: 2rem;
    align-items: center;
  }

  .header {
    display: grid;
    grid-template-columns: 1fr auto;
    gap: 1.5rem;
    align-items: center;
    h3 {
        letter-spacing: 1px;
        margin-bottom: 0;
        font-weight: 700;
        font-size: 2.2rem;
    }
  }

  .image-container {
    display: none;
  }
  /* .brand-img {
    width: 100%;
    min-height: 30rem;
    object-fit: cover;
    display: block;
    align-self: center;
  }  */
  .slogan {
    position: absolute;
    left: 0;
    bottom: 0;
    color: var(--white);
    text-transform: none;
    padding: 0.25rem 0.5rem;
    margin-bottom: 1.3rem;
    margin-inline: 1.3rem;
    font-size: 1.2rem;
    font-weight: 600;
  }
  .form {
    margin-top: 1rem;
    max-width: 95%;
    width: 100%;
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
  @media (min-width: 992px) {
    .page-layout {
      grid-template-columns: 1fr 1fr;
    }

  .image-container {
    display: block;
    min-height: 30rem;
    margin-top: 2rem;
    align-self: center;
    width: 100%;
    position: relative;
    overflow: hidden;
    border-radius: 6px;
    /* background-image: linear-gradient(to bottom, rgba(0,0,0,0), rgba(0,0,0,0.7)); */
  }

  .image-container::before {
    content: "";
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    background-image: linear-gradient(to bottom, rgba(0,0,0,0), rgba(0,0,0,0.5));
  }

  .brand-img {
    /* height: 100%; */
    width: 100%;
    min-height: 30rem;
    object-fit: cover;
    display: block;
    align-self: center;
    background-image: linear-gradient(to bottom, rgba(0,0,0,0), rgba(0,0,0,7));
  }

    .info {
      grid-template-columns: 1fr 1fr;
      column-gap: 3rem;
    }
    .main-img {
      display: block;
    }
  }
`
export default Wrapper