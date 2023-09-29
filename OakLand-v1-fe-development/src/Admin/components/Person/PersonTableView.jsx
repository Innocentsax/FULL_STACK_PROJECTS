import { useState } from 'react'
import { productColumns } from './personTableData'
import DrawerForm from '../Forms/DrawerForm'
import ReactPaginate from 'react-paginate';
import AddProductForm from '../Forms/AddProductForm'

import {
  LoadingOutlined,
  CaretLeftOutlined, 
} from '@ant-design/icons';

import { Table, Spin, Empty } from 'antd';
import usePerson from '../../../hooks/usePerson'
import useProduct from '../../../hooks/useProduct';
const antIcon = <LoadingOutlined style={{ fontSize: 80 }} spin />;

  const PersonTableView = ({ tableTitle }) => {
    const { headerTitle } = useProduct()
    const {persons, personTotalPages, setPersonPageNumber, personFetching, personTotalElements } = usePerson()

    const changePage = ({ selected }) => setPersonPageNumber(selected)
    const[showDrawer, setShowDrawer] = useState(false)

    return (
        <section className="table-data-section">
          <DrawerForm
            title={headerTitle}
            showDrawer={showDrawer}
            setShowDrawer={setShowDrawer}
            formLayout={<AddProductForm 
            setShowDrawer={ setShowDrawer } 
            />}
          />
          {
            personFetching &&
            <div style={{ width: "100vw", display: "flex", height:"100%", alignItems: "center", justifyContent: "center", }}>
                <Spin indicator={antIcon} style={{ color: "rgb(218, 196, 161)" }}/>
            </div>
          }


        <div className="table-div">
        {
          persons?.length > 0 &&          
          <div className="title-head"> 
              <div className='title-sub-head'>
                  <button style={{padding: "15px 30px"}}
                    className="btn-count">{personTotalElements}
                  </button>
              </div>
              <h2 className='"layout-h2-header'>{tableTitle}</h2>

              <ReactPaginate 
                previousLabel={<CaretLeftOutlined />}
                nextLabel={<CaretLeftOutlined />}
                pageCount={personTotalPages} 
                onPageChange={changePage}
                containerClassName={"paginationBtns"}
                previousLinkClassName={"prevBtn"}
                nextLinkClassName={"nextBtn"}
                disabledClassName={"paginationDisabled"}
                activeClassName={"paginationActive"}
              />
            </div> 
          }


          { !personFetching && persons?.length > 0 && 
            <Table 
              dataSource={ persons }
              rowkey={ person => person.id } 
              bordered
              pagination={false}
              scroll={{ x: '400px', y: 600 }}  
              columns={productColumns} 
            />
          }
          </div>

          { persons?.length === 0 && !personFetching && 
            <div style={{ width: "100vw", display: "flex", height:"100%", alignItems: "center", justifyContent: "center", }}>
              <Empty /> 
            </div>
          }   
        </section>
    )
  }

  export default PersonTableView