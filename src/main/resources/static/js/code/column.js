//js部分:2018-08-01T17:34:48.155+08:00
$UU.init({
    data: {
        loading: false,
        dialogFormVisible: false,
        importFormVisible: false,
        dialogTitle: "",
        search_group: {
            id:'',
            tableName:'',
            comments:''
        },
        data_group: {
            active: 'active',
            list: [{}, {}, {}],
            multipleSelection: [],
            multipleTable: [],
            comments:"",
            tableName:"",
            dbName:"",
            id:null,
        },
        form: {
            id:null,
            dbName: null,
            url: "",
            driver: "",
            username: "",
            password: "",
            schema: null,
            catalog: null,
            dbType:null
        },
        columnForm:{
            id:'',
            tableName:'',
            comments:'',
            remarks:[],
            dbId:null,
            prefix:null,
            modelName:'chehejia',
            author:'Auto-generator',
            entityName:'%s',
            mapperName:'%sMapper',
            xmlName:'%sMapper',
            serviceName:'I%sService',
            serviceImplName:'%sServiceImpl',
            controllerName:'%sController',
            entityPackage:'domain',
            servicePackage:'service',
            serviceImplPackage:'service.impl',
            mapperPackage:'mapper',
            controllerPackage:'controller',
        }
    },
    created: function () {
        this.search_group.id = $UF.getUrlParam("id");
        this.search_group.tableName = $UF.getUrlParam("tableName");
        this.search_group.comments = $UF.getUrlParam("comments");
        console.log("314314321",this.form)
        this.query();
        this.queryTableStrategy();
    },
    mounted: function () {
        console.log("vue mounted");
    },
    watch: {
        //监听数据
        "$data.data_group.multipleSelection": function (multipleSelection) {
            if (multipleSelection.toString().length > 0) {
                this.search_group.btn_disabled = false;
            } else {
                this.search_group.btn_disabled = true;
            }
        }
    },
    methods: {
        resetForm: function (formName) {
            var _this = this;
            _this.$refs[formName].resetFields();
            _this.search_group.vinListStr = '';
        },
        onCurrentPageChange: function (index) {
            var _this = this;
            _this.data_group.pagination.index=index;
            this.query();
        },
        onPageSizeChange: function (size) {
            var _this = this;
            _this.data_group.pagination.size=size;
            this.query();
        },
        queryTableStrategy: function(){
            var _this = this;
            $UU.http.get("/get-table-strategy",{id:_this.search_group.id,tableName:_this.search_group.tableName},function (response) {
                if(response.data.data != undefined) {
                    _this.columnForm.id = response.data.data.id;
                    _this.columnForm.prefix = response.data.data.prefix;
                    _this.columnForm.modelName = response.data.data.modelName;
                    _this.columnForm.author = response.data.data.author;
                    _this.columnForm.entityName = response.data.data.entityName;
                    _this.columnForm.mapperName = response.data.data.mapperName;
                    _this.columnForm.xmlName = response.data.data.xmlName;
                    _this.columnForm.serviceName = response.data.data.serviceName;
                    _this.columnForm.serviceImplName = response.data.data.serviceImplName;
                    _this.columnForm.controllerName = response.data.data.controllerName;
                    _this.columnForm.entityPackage = response.data.data.entityPackage;
                    _this.columnForm.servicePackage = response.data.data.servicePackage;
                    _this.columnForm.serviceImplPackage = response.data.data.serviceImplPackage;
                    _this.columnForm.mapperPackage = response.data.data.mapperPackage;
                    _this.columnForm.controllerPackage = response.data.data.controllerPackage;
                }
            });
        },
       query: function () {
           var _this = this;
            //请求参数
            var req = _this.search_group;
            $UU.http.get("/column-list",
                req
                , function (response) {
                    //获取回调数据
                    console.log(response.data);
                    _this.data_group.list = response.data.data.listColumn;
                    _this.data_group.comments=response.data.data.comments;
                    _this.data_group.tableName=_this.search_group.tableName;
                    _this.data_group.id=_this.search_group.id;
                }, {
                    requestBody: true,
                    before: function () {
                        _this.btn_disabled = true;
                    },
                    after: function () {
                        _this.btn_disabled = false;
                    }
                });
        },
        openDialog: function (type,id) {
            var _this = this;
            if (type === 'new') {
                _this.dialogFormVisible = true;
                _this.dialogTitle = "新增";
                _this.form = {
                    id:null,
                    dbName: null,
                    url: "",
                    driver: "",
                    username: "",
                    password: "",
                    schema: null,
                    catalog: null,
                    dbType:null
                }
            } else if (type === 'edit') {
                _this.dialogFormVisible = true;
                _this.dialogTitle = "编辑";
                var id = id;
                _this.getById(id);
            } else if (type === 'delete') {
                if (_this.data_group.multipleSelection.length === 0) {
                    _this.$message.warning("请选择一条数据！");
                }else {
                    var ids = '';
                    for(var i=0;i<_this.data_group.multipleSelection.length;i++){
                        if(i==0){
                            ids = _this.data_group.multipleSelection[i].id;
                        }else{
                            ids += ","+_this.data_group.multipleSelection[i].id;
                        }
                    }
                    _this.deleteById(ids);
                }
            }
        },
        submitForm: function (formName) {
            var _this = this;
            _this.$refs[formName].validate(function (valid) {
                if (valid) {
                    _this.$confirm('确定操作吗?', '提示', {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }).then(function () {
                        if (_this.dialogTitle === "新增") {
                            _this.save();
                        } else if (_this.dialogTitle === "编辑") {
                            _this.update();
                        }
                    }).catch(function () {
                    });
                }
            });
        },
        submitColForm: function (formName) {
            var _this = this;
            _this.$confirm('确定操作吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                console.log(666,_this.columnForm)
                _this.columnForm.id = _this.search_group.id;
                _this.columnForm.tableName = _this.search_group.tableName;
                _this.columnForm.comments = decodeURI(_this.search_group.comments);
                _this.data_group.list.forEach(function (e) {
                    var remark = e.colName+'@'+e.colType+'@'+e.comments+'@'+e.extra+'@'+e.nullable+'@'+(e.defaultValue==null?'':e.defaultValue);
                    _this.columnForm.remarks.push(remark);
                });
                console.log(7777, _this.columnForm);
                $UU.http.post("/columnsave", _this.columnForm, function (response) {
                    if(response.data.code === 0){
                        _this.$message.success(response.data.msg);
                    }else{
                        _this.$message.error(response.data.msg);
                    }
                }, {
                    before: function () {
                        _this.btn_disabled = true;
                    },
                    after: function () {
                        _this.btn_disabled = false;
                    }
                });
            }).catch(function (e) {
                console.log(e)
            });
        },
        onSelectionChange: function (val) {
            this.data_group.multipleSelection = val;
        },
        testConn: function(){
            var _this = this;
            $UU.http.post("/test",_this.form,function (response){
                if(response.data.code === 0){
                    _this.$message.success(response.data.msg);
                }else{
                    _this.$message.error(response.data.msg);
                }
            },{

            })
        },
        getById: function (id) {
            var _this = this;
            //请求参数
            var req = {};
            $UU.http.get("/getByDbId?id=" + id,
                req
                , function (response) {
                    //获取回调数据
                    console.log(response.data);
                    _this.form = response.data.data;
                }, {
                    requestBody: true,
                    before: function () {
                        _this.btn_disabled = true;
                    },
                    after: function () {
                        _this.btn_disabled = false;
                    }
                });
        },
        deleteById: function (row) {
            var _this = this;
            _this.$confirm('确定操作吗?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                //请求参数
                var req = {};
                $UU.http.get("/delete?id=" + row.id,
                    req
                    , function (response) {
                        //获取回调数据
                        console.log(response.data);
                        if (response.data.code === 0) {
                            _this.query();
                        } else {
                            _this.$message.error(response.data.msg);
                        }
                    });
            }).catch(function () {
            });
        },
        setData: function () {
            var _this = this;
        },
        save: function () {
            var _this = this;
            console.info(_this.form);
            $UU.http.post("/save",
                _this.form
                , function (response) {
                    //获取回调数据
                    console.log(response.data);
                    if (response.data.code === 0) {
                        _this.$message.success(response.data.msg);
                        _this.dialogFormVisible = false;
                        _this.query();
                    } else {
                        _this.$message.error(response.data.msg);
                    }
                }, {
                    requestBody: true,
                    before: function () {
                        _this.btn_disabled = true;
                    },
                    after: function () {
                        _this.btn_disabled = false;
                    }
                });
        },
        update: function () {
            var _this = this;
            _this.setData();
            $UU.http.put("/edit",
                _this.form
                , function (response) {
                    //获取回调数据
                    console.log(response.data);
                    if (response.data.code === 0) {
                        _this.$message.success(response.data.msg);
                        _this.dialogFormVisible = false;
                        _this.query();
                    } else {
                        _this.$message.error(response.data.msg);
                    }
                }, {
                    requestBody: true,
                    before: function () {
                        _this.btn_disabled = true;
                    },
                    after: function () {
                        _this.btn_disabled = false;
                    }
                });
        },
        handleCurrentChange: function (val) {
            if (null !== val) {
                this.data_group.multipleSelection = val.id;
            }
        },
        download: function () {
            var _this = this;
            _this.$confirm('你的配置是否保存?', '提示', {
                confirmButtonText: '确定',
                cancelButtonText: '取消',
                type: 'warning'
            }).then(function () {
                //请求参数
                window.open($UC.ctxPath+"/generate?tableName="+_this.data_group.tableName+"&id="+_this.data_group.id);
            }).catch(function () {
            });
            // window.location.href=$UC.ctxPath+"/generate?tableName="+_this.data_group.tableName+"&dbName="+_this.data_group.dbName;
        },
        saveForm: function () {

        }
    }
});

