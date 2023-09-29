import { Drawer, Button } from 'antd';

const DrawerForm = ({title, showDrawer, setShowDrawer, formLayout }) => {
    const onCLose = () => setShowDrawer(false);
    
    return <Drawer
        title={title}
        width={400}
        onClose={onCLose}
        open={showDrawer}
        bodyStyle={{paddingBottom: 80}}
        footer={
            <div
                style={{
                    textAlign: 'right',
                }}
            >
                <Button onClick={onCLose} style={{marginRight: 8}}>
                    Cancel
                </Button>
            </div>
        }
    >
    {formLayout}
    </Drawer>
}

export default DrawerForm;