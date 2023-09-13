import styled from 'styled-components'

const Wrapper = styled.div`

.img-container {
  overflow: hidden;
  /* border: 1px solid green; */
  position: relative;
  z-index: -999;
  width: 200px;
  height: 200px;
  border-radius: 2rem;
  margin: 0 auto;
  margin-bottom: 1.5rem;
}
.person-img {
  width: 100%;
  display: block;
  height: 100%;
  object-fit: cover;
  /* border-radius: 50%; */
  /* position: relative; */
}

.form {
  border: 1px solid var(--grey-300);
}

/* .form-icon {
  width: 20px;
  position: absolute;
  z-index: 99;
  left: 12px;
  bottom: 8px;
} */

.image-icon {
  width: 2.5rem;
  height: 2.5rem;
  cursor: pointer;
  /* border-radius: 50%; */
  border-radius: 100% 0% 89% 11% / 61% 53% 47% 39% ;
  background-color: var(--primary-500);
  display: grid;
  place-items: center;
  position: absolute;
  right: -5px;
  bottom: -1px;
  z-index: 9;
  pointer-events: auto;
}

svg {
  width: 1rem;
  height: 1rem;
}

`

export default Wrapper