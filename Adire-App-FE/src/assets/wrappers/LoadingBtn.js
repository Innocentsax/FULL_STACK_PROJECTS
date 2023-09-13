import styled from 'styled-components'

const Wrapper = styled.div`
button {
    color: white;
    background-color: #1D4ED8;
    --ring-color: #93C5FD;
    font-weight: 500;
    border-radius: 0.5rem;
    font-size: 1rem;
    line-height: 2rem;
    padding-left: 2rem;
    padding-right: 2rem;
    padding-top: 0.7rem;
    padding-bottom: 0.7rem;
    text-align: center;
    margin-right: 0.5rem;
    display: inline-flex;
    align-items: center;
    border: none;
  }
  
  button:hover {
    background-color: #1E40AF;
  }
  
  button:focus {
    box-shadow: var(--tw-ring-inset) 0 0 0 calc(4px + var(--tw-ring-offset-width)) var(--tw-ring-color);
  }
  
  button svg {
    display: inline;
    width: 1.3rem;
    height: 1.3rem;
    margin-right: 0.75rem;
    color: white;
    animation: spin_357 1s linear infinite;
  }
  
  @keyframes spin_357 {
    from {
      transform: rotate(0deg);
    }
  
    to {
      transform: rotate(360deg);
    }
  }

`

export default Wrapper