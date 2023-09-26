import React from "react";
import {
  CloseBtn,
  GoodIcon,
  LabelModal,
  ParaModal,
  ModalCard,
  CModal,
} from "../Styled/Styled";

const Passwordmodal = () => {
  return (
    <CModal>
      <ModalCard>
        <GoodIcon />
        <LabelModal>Successful</LabelModal>
        <ParaModal>Your password has been changed successfully.</ParaModal>
        <CloseBtn type="submit">Close</CloseBtn>
      </ModalCard>
    </CModal>
  );
};

export default Passwordmodal;
