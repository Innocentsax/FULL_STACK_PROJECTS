import styled from 'styled-components'

const Wrapper = styled.section`
  /* display: grid;
  align-items: center; */
  .logo {
    display: block;
    margin: 0 auto;
    margin-bottom: 1.38rem;
  }
  .form {
    margin-top: 0;
    max-width: 400px;
    border-top: 5px solid var(--primary-500);
  }
  h3 {
    text-align: center;
  }
  p {
    margin: 0;
    margin-top: 1rem;
    text-align: center;
  }
  .btn {
    margin-top: 1rem;
  }
  .member-btn {
    background: transparent;
    border: transparent;
    color: var(--primary-500);
    cursor: pointer;
    letter-spacing: var(--letterSpacing);
  }
  
  .forgot-password-text {
    font-size: var(--extra-small-text);
    margin-top: -0.3rem;
    display: block;
    color: var(--primary-500);
  }


  .google {
  display: inline-flex;
  padding: 0.5rem 1rem;
  background-color: #DC2626;
  color: #ffffff;
  font-size: 1rem;
  line-height: 1.5rem;
  font-weight: 500;
  justify-content: center;
  width: 100%;
  border-radius: 0.375rem;
  border-width: 1px;
  border-color: transparent;
  box-shadow: 0 1px 2px 0 rgba(0, 0, 0, 0.05);
}


`
export default Wrapper