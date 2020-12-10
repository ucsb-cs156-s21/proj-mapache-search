import { fetchWithToken } from "main/utils/fetch";

const buildCreateStudent = (getToken, onSuccess, onError) => {
  const func = async (item) => {
    try {
      await fetchWithToken(`/api/students`, getToken, {
        method: "POST",
        headers: {
          "content-type": "application/json",
        },
        body: JSON.stringify(item),
      });
      onSuccess();
    } catch (err) {
      onError(err);
    }
  };
  return func
}

const buildUpdateStudent = (getToken, onSuccess, onError) => {
  const func = async (item, id) => {
    try {
        await fetchWithToken(`/api/students/${id}`, getToken, {
            method: "PUT",
            headers: {
            "content-type": "application/json",
            },
            body: JSON.stringify(item),
        });
        onSuccess();
    } catch (err) {
        onError(err);
    }
  };
  return func;
}

const buildDeleteStudent = (getToken, onSuccess, onError) => {
  const func = async (id) => {
    try {
      await fetchWithToken(`/api/students/${id}`, getToken, {
        method: "DELETE",
        headers: {
          "content-type": "application/json",
        },
        noJSON: true,
      });
      onSuccess();
    } catch (err) {
      onError(err);
    }
  };
  return func;
}

const buildDeleteAllStudents = (getToken, onSuccess, onError) => {
    const func = async () => {
        try {
            await fetchWithToken(`/api/students`, getToken, {
                method: "DELETE",
                headers: {
                    "content-type": "application/json",
                },
                noJSON: true,
            });
            onSuccess();
        } catch (err) {
            onError(err);
        }
    };
    return func;
}

const uploadStudentsCSV = (getToken, onSuccess, onError) =>{
    const func = async (file) => {
        const data = new FormData();
        data.append("csv", file);
        try{
            await fetchWithToken('/api/students/upload', getToken, {
                method: "POST",
                body: data
            });
            onSuccess();
        } catch (err){
            onError(err);
        }
    };
    return func;
}
export { buildCreateStudent, buildDeleteStudent, buildUpdateStudent, buildDeleteAllStudents, uploadStudentsCSV};