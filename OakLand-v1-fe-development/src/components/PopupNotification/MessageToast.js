    export const notification = (type, content, messageApi) => {
        messageApi.open({
          type: type,
          content: content,
        });
    };