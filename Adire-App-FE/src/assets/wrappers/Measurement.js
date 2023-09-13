import styled from 'styled-components'

const Wrapper = styled.div`

    display: grid;
    grid-template-columns: 1fr 1fr;
    column-gap: 2rem;
    /* border: 1px solid red; */
    width: 100%;
    max-width: var(--fixed-width);
    background: var(--white);
    border-radius: var(--borderRadius);
    box-shadow: var(--shadow-2);
    transition: var(--transition);
    padding: 1rem 1.5rem;


.form-row {
    display: grid;
    grid-template-columns: 1fr auto;
}

.form-input-default {
    height: 3rem;
    width: 6rem;
}

.form-label {
    font-size: .85rem;
    align-self: center;
}

`


export default Wrapper