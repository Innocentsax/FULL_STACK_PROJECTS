import React from 'react';
import { useState } from "react";
import Passwordform from './Passwordform';
import { ContainerFBG, LabelHeader, FormCard, TabDiv, ButtonTab, TogDiv } from '../Styled/Styled';
import Pinform from './Pinform';

const SecuritySetting = () => {

    const [toggleState, setToggleState] = useState(1);

    const toggleTab = (index) => {
        setToggleState(index);
    };

  return (
    <ContainerFBG>
      <LabelHeader>
        Security Setting
      </LabelHeader>
      

      <FormCard>
        <TabDiv>
            <ButtonTab
                className={toggleState === 1 ? 'active' : ''}
                onClick={() => toggleTab(1)}
            >
                Change Password
            </ButtonTab>

            <ButtonTab
                className={toggleState === 2 ? 'active' : ''}
                onClick={() => toggleTab(2)}
            >
                Change Transaction Pin
            </ButtonTab>
        </TabDiv>

        <TogDiv
            className={toggleState === 1 ? "active" : ""}
        >
            <Passwordform />
        </TogDiv>

        <TogDiv
            className={toggleState === 2 ? "active" : ""}
        >
            <Pinform />
        </TogDiv>
        
        
      </FormCard>

    </ContainerFBG>
  )
}

export default SecuritySetting
