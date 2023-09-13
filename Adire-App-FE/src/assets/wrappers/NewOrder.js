import styled from 'styled-components'

const Wrapper = styled.section`
.new-order-container {
    display: grid;
    grid-template-columns: 1fr;
    gap: 1rem;
    /* border: 1px solid green; */
}

.form {
    /* border: 1px solid red; */
    width: 100%;
    max-width: 100%;
    /* max-width: 80%; */
    /* background: var(--white); */
    background: transparent;
    border-radius: var(--borderRadius);
    /* box-shadow: var(--shadow-2); */
    box-shadow: none;
    padding: 0.5rem 1rem;
    margin: 0;
    transition: var(--transition);
}
.measurement-container {
    h4 {
        margin-bottom: 0.5rem;
        font-weight: 600;
    }
}


.material-btn {
    text-transform: capitalize;
    color: var(--primary-500);
    border: 1px solid var(--primary-500);
}

.form-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    column-gap: 1rem;
}

.image-grid {
    display: grid;
    grid-template-columns: 1fr 1fr;
    gap: 1rem;
}

.header {
    /* padding: 1rem 1.5rem; */
    border-bottom: 1px solid var(--grey-100);
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

  .btn {
    text-transform: none;
    padding: .6rem 1rem;
  }

  .btn-block {
    margin-top: 1.2rem;
  }

  .bg-screen{
    display: none;
  }

  .sm-screen{
    display: block;
  }

  .file-inputs {
    position: relative;
  }

  .image-input {
    position: relative;
    height: 40px;
    width: 100%;
    z-index: 2;
    cursor: pointer;
    opacity: 0;
  }

  .image-btn {
    position: absolute;
    top: 0px;
    left: 0px;
    width: 100%;
    height: 100%;
    z-index: 1;
    display: flex;
    justify-content: center;
    align-items: center;

    color: var(--primary-500);
    /* background-color: var(--primary-500); */
    background-color: var(--white);
    font-size: 1.1rem;
    cursor: pointer;
    border-radius: var(--borderRadius);
    /* border: none; */
    border: 1px solid var(--primary-500);
    outline: none;
    /* box-shadow: 0px 8px 24px rgba(149, 157, 165, 0.5); */
  }


  i {
    color: var(--primary-500);
    margin: 0.8em;
    font-size: 0.8em;
  }

  /* input[type="file"] {
    display: none;
  } */

  /* .something {
    color: var(--white);
    height: 60px;
    width: 250px;
    background-color: var(--primary-500);
    display: flex;
    justify-content: center;
    align-items: center;
  } */


  /* .view-all-btn {
    background: transparent;
    border-color: transparent;
    color: var(--primary-500);
    letter-spacing: var(--letterSpacing);
    text-transform: capitalize;
    cursor: pointer;
  } */

@media (min-width: 1160px) {

    .new-order-container {
        grid-template-columns: repeat(2, 1fr);
    }

    .bg-screen{
        display: block;
    }
    .sm-screen{
        display: none;
    }

    /* .form-center {
      grid-template-columns: 1fr 1fr;
      align-items: center;
      column-gap: 1rem;
    }
    .btn-container {
      margin-top: 0;
    } */
  }

`

export default Wrapper