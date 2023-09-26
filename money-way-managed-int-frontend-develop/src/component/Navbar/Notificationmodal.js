import React from 'react'
import { LPdiv, LabelPM, ParaPM, NHdiv } from '../Styled/Styled'

const Notificationmodal = () => {
  return (
        <>
            <NHdiv>
                <LabelPM>Notification</LabelPM>
            </NHdiv>
            <LPdiv style={{ margin: 'auto auto', textAlign: 'center' }}>
                <LabelPM>Nothing yet</LabelPM>
                <ParaPM style={{ marginTop: '16px' }}>You do not have any notification</ParaPM>
            </LPdiv>
        </>
  )
}

export default Notificationmodal
