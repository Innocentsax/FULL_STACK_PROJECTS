import styled from 'styled-components';

export const App =styled.div`
width:80%;
height:auto;
overflow:scroll;
background:#f8f8f8;
padding:20px
`

export const DivIm =styled.div`
width:100%;
height:150px;
img{
    width:90%;
    height:100%
}
`

export const AppContent =styled.div`
width:90%;
height:300px;
margin-top:30px;
display:flex;
flex-direction:column;
align-items:center;
justify-content:center

`

export const LoanModar = styled.div`
width:100%;
height:100vh;
position:fixed;
z-index:999;
top:0px;
background:rgba(0,0,0,0.5);
display:flex;
justify-content:center;
align-items:center
`

export const RoleModar =styled.div`
width:100%;
height:40px;
boder-bottom:1px solid #ccc;
display:flex;
justify-content:space-between;
align-items:center;
button{
    width:20px;
    height:20px;
    border-radius:40px;
    font-size:10px;
    border:0px;
}
`
export const RoleModar2 =styled.div`
width:100%;
height:auto;
boder-bottom:1px solid #ccc;
display:flex;
justify-content:space-between;
align-items:center;
flex-direction:column

`
export const FormI =styled.form`
width:100%;
label{
    margin:top:10px
}

`

export const DivI =styled.div`

width:100%;
height:auto;
padding:10px;
background:white;
display:flex;
`
export const DivImp = styled.div`
width:350px;
height:150px;
border-radius:10px;
border:1px solid #ccc;
margin:10px;
padding:10px
`

export const DivImp2 = styled.div`
width:350px;
height:150px;
border-radius:10px;
border:1px solid #ccc;
margin:10px;
padding:10px;
p{
    margin:0px;
    font-size:12px;
}
`

export const SidedTfLX =styled.div`
width:90%;
height:40px;
display:flex;
align-items:center;
margin-bottom:10px;
margin-left:10px;
img{
    margin-right:10px;
}
span{
    color:#667085
}
`