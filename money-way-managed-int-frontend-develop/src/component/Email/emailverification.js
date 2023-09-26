import React from "react";
import { ButtonP, ContainerLG, ContainerV, Form, LGCard, LabelHeaderI, LabelHeaderV, VCard, VPara } from "../Styled/Styled";


function EmailVerification(){

    return(
        <ContainerV>
            <VCard>
                <LabelHeaderV>Verify your email</LabelHeaderV>
                    <VPara>Hi there, use the link below to verify<br></br>your email and start enjoying MoneyWay.</VPara>
                        
                    {/*<ButtonP>Resend Link</ButtonP>*/}
            </VCard>
        </ContainerV>
    );
}

export default EmailVerification;