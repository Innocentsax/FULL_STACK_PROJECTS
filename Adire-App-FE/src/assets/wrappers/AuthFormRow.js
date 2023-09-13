import styled from 'styled-components'


const Wrapper = styled.section`
.input_container {
  width: 100%;
  height: fit-content;
  position: relative;
  display: flex;
  flex-direction: column;
  gap: 5px;
}
.input_field {
  padding: 0 0 0 40px;
  outline: none;
  transition: all 0.3s cubic-bezier(0.15, 0.83, 0.66, 1);
}
.input_field {
  width: auto;
  height: 40px;
  padding: 0 0 0 40px;
  border-radius: 7px;
  outline: none;
  border: 1px solid #e5e5e5;
  filter: drop-shadow(0px 1px 0px #efefef)
    drop-shadow(0px 1px 0.5px rgba(239, 239, 239, 0.5));
  transition: all 0.3s cubic-bezier(0.15, 0.83, 0.66, 1);
}
.form-icon {
  width: 20px;
  position: absolute;
  z-index: 99;
  left: 12px;
  bottom: 9px;
}

`