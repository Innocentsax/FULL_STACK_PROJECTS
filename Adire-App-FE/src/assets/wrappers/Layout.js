import styled from 'styled-components'

const Wrapper = styled.main`
  nav {
    width: var(--fluid-width);
    max-width: var(--max-width);
    margin: 0 auto;
    height: var(--nav-height);
    display: flex;
    align-items: center;
  }

  .section {
    padding-inline: 1rem;
    padding-top: 3rem;
    /* display: grid;
    justify-content: center;
    align-items: flex-start; */
    min-height: calc(100vh - var(--nav-height));
    /* border: 3px solid purple; */
  }

`
export default Wrapper