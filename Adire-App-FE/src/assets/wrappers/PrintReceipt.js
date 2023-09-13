import styled from 'styled-components'

const Wrapper = styled.div`
    margin-block: 4rem;

.container{
    max-width: 1120px;
    width: 90%;
    padding-right: 15px;
    padding-left: 15px;
    margin: 0 auto;
    min-height: calc(100vh - 13rem);
}

.content {
    display: flex;
    justify-content: space-between;
    align-items: center;
    height: 4rem;
}

.receipt-title,
.receipt-header {
    margin: 0;
    color: var(--primary-500);
    text-transform: uppercase;
    font-weight: 300;
    font-size: 1.2rem;
}

.text-capitalize {
    text-transform: capitalize;
}

.title {
    color: var(--primary-500);
    text-transform: uppercase;
}
.dot {
    color: orange;
    font-size: 3rem;
}
.header {
    margin-top: 0.6rem;
    margin-bottom: 0%.8rem;
}
.receipt-title {
    font-size: 0.8rem;
}

.text-small {
    text-transform: capitalize;
    font-size: 0.65rem;
}

.receipt-text {
    margin-top: 0.5rem;
}

.copyright {
    text-transform: capitalize;
    letter-spacing: var(--letterSpacing);
    color: var(--primary-500);
    text-align: center;
    margin-inline: auto;
}

footer {
  width: 100%;
  background-color: #f5f5f5;
  padding: 10px;
}



`

export default Wrapper