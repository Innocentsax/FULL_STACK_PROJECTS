  import { useState } from 'react'
  import { stateColumns } from './statesTableData'
  import DrawerForm from '../Forms/DrawerForm';
  import StatesForm from '../Forms/StatesForm';

  import { LoadingOutlined, } from '@ant-design/icons';
  import { Table, Spin, Empty } from 'antd';
  import { GpsFixedSharp, Warehouse } from '@mui/icons-material';
  import useCategory from '../../../hooks/useCategory';
  import useProduct from '../../../hooks/useProduct';

  const antIcon = <LoadingOutlined style={{ fontSize: 80 }} spin />;

  const StatesTableView = ({ tableTitle }) => {
    const { states, fetching, 
      totalStates, setSingleState, } = useCategory()
    const { setHeaderTitle, headerTitle } = useProduct()

    const[showDrawer, setShowDrawer] = useState(false)

    const handleShowDrawer = () => {
      setHeaderTitle("Add New State")
      setSingleState({ nameOfState: "", })
      setShowDrawer(!showDrawer);
    }

    return (
        <section className="table-data-section">
          <DrawerForm
             title={headerTitle}
             showDrawer={showDrawer}
              setShowDrawer={setShowDrawer}
              formLayout={<StatesForm
                setShowDrawer={ setShowDrawer } 
            />}
          />
          {fetching &&
            <div style={{ width: "100vw", display: "flex", height:"100%", alignItems: "center", justifyContent: "center", }}>
                <Spin indicator={antIcon} style={{ color: "rgb(218, 196, 161)" }}/>
            </div>
          }
          
            <div className="table-div">
            {
              states?.length > 0 &&   
              <div className="title-head"> 
                <div className='title-sub-head'>
                  <button className="home-btn" onClick={handleShowDrawer}>
                    <GpsFixedSharp />Add New State
                  </button>
                    <button className="btn-count">{totalStates}</button>
                </div>
                <h2 className='"layout-h2-header'>{tableTitle}</h2>
              </div> 
            }

            {states?.length > 0 && 
              <Table 
              dataSource={ states }
              rowkey={ state => state.id } 
              bordered
              pagination={true}
              scroll={{ x: '400px', y: 600 }}  
              columns={stateColumns(setShowDrawer)} 
          
        />
      }
      </div>

          { states?.length === 0 && !fetching && 
            <div style={{ width: "100vw", display: "flex", height:"100%", 
              alignItems: "center", justifyContent: "center", }}>
              <Empty> 
                <button className="home-btn" onClick={handleShowDrawer}>
                  <GpsFixedSharp />Add New State
                </button>
              </Empty>
            </div>
          }    
      </section>
    )
  }

  export default StatesTableView