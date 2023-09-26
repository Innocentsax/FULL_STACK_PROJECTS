import React from "react";
import {
  CloseBtn,
  GoodIcon,
  LabelModal,
  ParaModal,
  ModalCard,
  CModal,
} from "../Styled/Styled";

const Pinmodal = () => {
  return (
    <CModal>
      <ModalCard>
        <GoodIcon />
        <LabelModal>Successful</LabelModal>
        <ParaModal>Your pin has been changed successfully.</ParaModal>
        <CloseBtn type="submit">Close</CloseBtn>
      </ModalCard>
    </CModal>
  );
};

export default Pinmodal;
