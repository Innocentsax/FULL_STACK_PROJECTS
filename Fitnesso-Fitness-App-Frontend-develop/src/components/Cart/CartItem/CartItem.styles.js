import styled from 'styled-components'

export const Wrapper = styled.div`
  display: flex;
  justify-content: space-between;
  font-family: Arial, Helvetica, sans-serif;
  border-bottom: 1px solid lightblue;
  padding-bottom: 20px;

  .cart_div {
    flex: 1;
  }

  .cart_information,
  .cart_buttons {
    display: flex;
    justify-content: space-between;
  }

  .cart_img {
    max-width: 80px;
    object-fit: cover;
    margin-left: 40px;
  }
`