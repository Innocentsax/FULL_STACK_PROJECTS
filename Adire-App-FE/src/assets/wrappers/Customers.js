import styled from 'styled-components'

const Wrapper = styled.div`
    height: 100%;
    display: grid;
    place-items: center;

    div {
        margin-top: 3rem;
        display: grid;
        place-items: center;
    }
    p{
        margin: 0;
    }
    .oh-no{
        font-size: 3rem;
        font-weight: 700;
        text-transform: capitalize;
        margin-top: 1rem;
    }
    .btn-outline {
        cursor: pointer;
        color: var(--primary-500);
        background: transparent;
        border: 1px solid var(--primary-500);
        padding: 0.7rem 1.5rem;
        margin-top: 1rem;

        transition: all 1000ms;
        position: relative;
        overflow: hidden;
    }

    .btn-outline:hover {
        color: #ffffff;
        /* outline: 2px solid #70bdca; */
        box-shadow: 4px 5px 17px -4px var(--primary-800);
    }

    .btn-outline::before {
    content: "";
    position: absolute;
    left: -50px;
    top: 0;
    width: 0;
    height: 100%;
    background-color: var(--primary-500);
    transform: skewX(45deg);
    z-index: -1;
    transition: width 1000ms;
    }

    .btn-outline:hover::before {
    width: 250%;
    }

`

export default Wrapper