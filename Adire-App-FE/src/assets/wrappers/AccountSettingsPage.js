import styled from 'styled-components'

const Wrapper = styled.section`
    /* border: 1px solid red; */

    .settings-title {
      text-transform: capitalize;
      font: 2rem;
    }
    
  h4 {
    width: 85%;
    color: var(--white);
    margin: 0 auto;
    /* border: 1px solid red; */
    text-align: center;
    background: rgb(189,168,241);
    background: linear-gradient(90deg, rgba(189,168,241,1) 0%, rgba(89,37,220,1) 29%, rgba(36,15,88,1) 81%);
    border-radius: .5rem;
    padding-block: 0.7rem;
  }
  .account-center {
    /* border: 1px solid red; */
    width: 85%;
    margin: 2rem auto 0;
    /* max-width: 900px; */
  }

  .btn-container {
  display: flex;
  flex-direction: row;
  justify-content: center;
  gap: 0.5rem;
  flex-wrap: wrap;
  margin-bottom: 4rem;
}

.item-container {
  width: 100%;
}

.settings-btn {
  background: transparent;
  border-color: transparent;
  /* letter-spacing: var(--letterSpacing); */
  cursor: pointer;
  transition: var(--transition);
  text-transform: capitalize;
}
.settings-btn:hover {
  color: var(--primary-500);
  box-shadow: 0 2px var(--primary-500);
}
.active-btn {
  color: var(--primary-500);
  box-shadow: 0 2px var(--primary-500);
}

@media (min-width: 992px) {
    .account-center {
        width: 95%;
        /* max-width: 900px; */
        display: grid;
        grid-template-columns: 200px 1fr;
        column-gap: 2rem;
    }

    .btn-container {
      display: grid;
      align-self: start;
        flex-direction: column;
        gap: 2rem;
        margin-bottom: 0;
        background-color: #fff;
        padding: 1rem 1rem;
    }

    .settings-btn:hover {
        color: var(--primary-500);
        box-shadow: 0 0 transparent;
    }
    .active-btn {
        color: var(--primary-500);
        box-shadow: 0 0 transparent;
    }
    .item-container {
      padding-left: 3rem;
      border-left: 1px solid var(--grey-300);
      min-height: 70vh;
    }

    h4 {
    width: 95%;
  }

}



  
`
export default Wrapper