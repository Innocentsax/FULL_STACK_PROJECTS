import React from 'react';

class ErrorBoundaryPage extends React.Component{
    constructor(props){
        super(props);
        this.state={hsaError:false}
    }
    static getDerivedStateFromError(error){
        return {
            hsaError:true
        }
    }

    render(){
            if(this.state.hsaError) return(
                <h1>Error image here</h1>
            );
        return(
            {this.props.children}
        );
    }
}


export default ErrorBoundaryPage;