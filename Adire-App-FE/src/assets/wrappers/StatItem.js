import styled from 'styled-components'

const Wrapper = styled.article`
    background: var(--white);
    border-radius: var(--borderRadius);
    display: grid;
    grid-template-columns: 1fr auto;
    align-items: center;
    column-gap: 1rem;
    padding: 2rem;
    box-shadow: var(--shadow-2);
    /* border-bottom: 5px solid ${(props) => props.color}; */

  
  .icon {
    width: 50px;
    height: 50px;
    display: grid;
    place-items: center;
    background: ${(props) => props.bcg};
    border-radius: 50%;
    font-size: 1.5rem;
    font-weight: 700;
    color: var(--white);
    svg {
        stroke: var(--primary-500);
        font-size: 2rem;
        color: ${(props) => props.color};
    }
  }

  .count {
    display: block;
    font-weight: 700;
    font-size: 40px;
    line-height: 1.25;
    /* color: ${(props) => props.color}; */
    color: var(--black);
  }
  .title {
    margin: 0;
    text-transform: capitalize;
    letter-spacing: var(--letterSpacing);
    text-align: left;
    margin-top: 0.5rem;
    font-size: 1.1rem;
  }
  
`

export default Wrapper