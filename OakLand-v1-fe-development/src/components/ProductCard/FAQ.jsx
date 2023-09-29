import './faq.css'
import Collapsible from '../ProductCard/Collapsible';
import { Collapse } from 'antd';

const { Panel } = Collapse;

const FAQ = ({data, title}) => {
  return (
    <div className="faq-div">
        <div className="heading">
            <hr/>
            <h3>{title}</h3>
            <hr/>
        </div> 
        <div className="collapse-div">
            <Collapsible>
                  {
                  data.filter((_, index) => index < 3).map(({ id, question, answer }) => 
                      <Panel header={question} key={id} 
                        className="site-collapse-custom-panel"
                      >
                          <p className="answer">{answer}</p>
                      </Panel>
                  )
                  }
              </Collapsible>

              <Collapsible>
                  {
                  data.filter((_, index) => index >= 3 && index < 6).map(({ id, question, answer }) => 
                      <Panel header={question} key={id}
                        className="site-collapse-custom-panel"
                      >
                          <p className="answer">{answer}</p>
                      </Panel>
                    )
                }
            </Collapsible>      
        </div>
    </div>
  )
}

export default FAQ