  import { useState } from 'react'
  import { categoryColumns } from './subCatTableData'
  import DrawerForm from '../Forms/DrawerForm';

  import { LoadingOutlined, } from '@ant-design/icons';
  import { Table, Spin, Empty } from 'antd';
  import { Category, } from '@mui/icons-material';
  import useCategory from '../../../hooks/useCategory';
  import useProduct from '../../../hooks/useProduct';
  import CategorySubCategoryForm from '../Forms/CategorySubcatForm';

  const antIcon = <LoadingOutlined style={{ fontSize: 80 }} spin />;

  const SubcategoryTableView = ({ tableTitle }) => {
    const { categories, fetching, 
      totalCategories, setSingleState, } = useCategory()
    const { setHeaderTitle, headerTitle } = useProduct()
    const[showDrawer, setShowDrawer] = useState(false)

    const handleShowDrawer = () => {
      setHeaderTitle("Add New Category")
      setSingleState({ name: "", })
      setShowDrawer(!showDrawer);
    }

    return (
        <section className="table-data-section">
          <DrawerForm
             title={headerTitle}
             showDrawer={showDrawer}
              setShowDrawer={setShowDrawer}
              formLayout={<CategorySubCategoryForm
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
              categories?.length > 0 &&   
              <div className="title-head"> 
                <div className='title-sub-head'>
                  <button className="home-btn" onClick={handleShowDrawer}>
                    <Category />Add New Category
                  </button>
                    <button className="btn-count">{totalCategories}</button>
                </div>
                <h2 className='"layout-h2-header'>{tableTitle}</h2>
              </div> 
            }

            {categories?.length > 0 && 
              <Table 
              dataSource={ categories }
              rowkey={ category => category.id } 
              bordered
              pagination={true}
              scroll={{ x: '400px', y: 600 }}  
              columns={categoryColumns(setShowDrawer)} 
          
        />
      }
      </div>

          { categories?.length === 0 && !fetching && 
            <div style={{ width: "100vw", display: "flex", height:"100%", 
              alignItems: "center", justifyContent: "center", }}>
              <Empty> 
                <button className="home-btn" onClick={handleShowDrawer}>
                  <Category />Add New Category
                </button>
              </Empty>
            </div>
          }    
      </section>
    )
  }

  export default SubcategoryTableView