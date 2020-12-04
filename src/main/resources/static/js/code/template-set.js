Vue.use(window.VueCodemirror);
//js部分:2019-11-05T11:03:17.264+08:00
    $UU.init({
        data: {
            loading: false,
            dialogFormVisible: false,
            templateDialogVisible:false,
            importFormVisible: false,
            dialogTitle: "",
            tName:'',
            curId:'',
            codemirror:null,
            search_group: {
                active: 'active',
                btn_disabled: false,
                vinListStr:'',

                id:null, // ID
                setName:null, // 组名
                desc:null, // 描述
                createUser:null, // 创建人

                vinList: [],
                pageUrl:""
            },
            data_group: {
                active: 'active',
                list: [{}, {}, {}],
                multipleSelection: [],
                multipleTable: [],
                //分页
                pagination: {
                    index: 1, //当前页码
                    size: 10, //每页记录数
                    total: 100 //记录总数
                }
            },
            form: {

                id:null, // ID
                setName:null, // 组名
                desc:null, // 描述
                createUser:null, // 创建人

            },
            rules: {

                id:[
                    { required: true, message: '请输入ID', trigger: 'blur' }
                ],
                setName:[
                    { required: true, message: '请输入组名', trigger: 'blur' }
                ],
                desc:[
                    { required: true, message: '请输入描述', trigger: 'blur' }
                ],
                createUser:[
                    { required: true, message: '请输入创建人', trigger: 'blur' }
                ],

            },
            templateList:[],
            templateForm:{

            },
            cmOptions: {
                smartIndent: true,    // 是否智能缩进
                lineNumbers: true,     // 显示行数
                indentUnit: 2,         // 缩进单位为4
                tabSize: 4,            // tab单位4
                styleActiveLine: true, // 当前行背景高亮
                matchBrackets: true,   // 括号匹配
                autoCloseTags: true,    //自动关闭标签 addon/edit/
                autoCloseBrackets: true, //自动输入括号
                continueComments: "Enter",
                mode: 'text/html',     // javascript混合模式
                lineWrapping: true,     // 自动换行
                foldGutter: true,
                gutters: ["CodeMirror-linenumbers", "CodeMirror-foldgutter"],
                theme: 'dracula',      // dracula
                keyMap: 'default',
                hint: CodeMirror.hint.sql,
                extraKeys: {
                    /*// F11键切换全屏
                    "F11": function (cm) {
                        cm.setOption("fullScreen", !cm.getOption("fullScreen"));
                    },
                    // Esc键退出全屏
                    "Esc": function (cm) {
                        if (cm.getOption("fullScreen")) cm.setOption("fullScreen", false);
                    },*/
                    "Alt-Enter": 'autocomplete', //唤起智能提示
                    "Ctrl-/": "toggleComment", //注释代码
                    "Ctrl-Alt-L": function autoFormat(cm) {
                        var totalLines = cm.lineCount();
                        cm.autoFormatRange({line: 0, ch: 0}, {line: totalLines});
                    }
                }
            },

        },
        created: function () {
            console.log("vue created");
            this.query();
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
            query: function (pageNo) {
                var _this = this;
                //清空选中的数据
                _this.data_group.multipleSelection="";
                //处理VIN
                var vinStr = $UF.getTextAreaArray(_this.search_group.vinListStr);
                if (vinStr.length > 0) {
                    _this.search_group.vinList = (vinStr).split(",");
                }
                if(!!pageNo){
                    _this.data_group.pagination.index = pageNo;
                }
                //请求参数
                var req = {
                    pageNo: _this.data_group.pagination.index,
                    pageSize: _this.data_group.pagination.size,
                    param: _this.search_group
                };
                $UU.http.post("/template-set/list",
                    req
                    , function (response) {
                        //获取回调数据
                        console.log(response.data);
                        if (response.data.code === 0) {
                            _this.data_group.list = response.data.data;
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
            openDialog: function (type) {
                var _this = this;
                if (type === 'new') {
                    _this.dialogFormVisible = true;
                    _this.dialogTitle = "新增";
                    _this.form = {

                        id:null, // ID
                        setName:null, // 组名
                        desc:null, // 描述
                        createUser:null, // 创建人

                    }
                } else if (type === 'edit') {
                    if (_this.data_group.multipleSelection.length != 1) {
                        _this.$message.warning("请选择一条数据！");
                    }else {
                        _this.dialogFormVisible = true;
                        _this.dialogTitle = "编辑";
                        var id = _this.data_group.multipleSelection[0].id;
                        _this.getById(id);
                    }
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
            onSelectionChange: function (val) {
                this.data_group.multipleSelection = val;
            },
            getById: function (id) {
                var _this = this;
                //请求参数
                var req = {};
                $UU.http.get("/template-set/get-by-id/" + id,
                    req
                    , function (response) {
                        //获取回调数据
                        console.log(response.data);
                        if (response.data.code === 0) {
                            _this.form = response.data.data;
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
            deleteById: function (id) {
                var _this = this;
                _this.$confirm('确定操作吗?', '提示', {
                    confirmButtonText: '确定',
                    cancelButtonText: '取消',
                    type: 'warning'
                }).then(function () {
                    //请求参数
                    var req = {};
                    $UU.http.delete("/template-set/delete-by-id/" + id,
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
                $UU.http.post("/template-set/save",
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
                $UU.http.put("/template-set/update",
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
            handleClick:function (row){
                this.curId = row.id;
                this.fetchFilesBySetId(this.curId)
            },
            fetchFilesBySetId:function (id){
                let _this = this;
                $UU.http.post("/template/list",
                    {templateSetId:id}
                    , function (response) {
                        //获取回调数据
                        console.log(response.data);
                        if (response.data.code === 0) {
                            _this.templateList = response.data.data;
                            // _this.templateForm = _this.templateList[0]
                            _this.templateDialogVisible = true;
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
            addTemplate: function () {
                var _this = this;
                if(!!!_this.tName){
                    _this.$message.error("文件名不能为空");
                    return;
                }
                $UU.http.post("/template/save",
                    {
                        templateName: _this.tName,
                        templateSetId: _this.curId
                    }
                    , function (response) {
                        //获取回调数据
                        console.log(response.data);
                        if (response.data.code === 0) {
                            _this.$message.success(response.data.msg);
                            _this.dialogFormVisible = false;
                            _this.fetchFilesBySetId(_this.curId);
                            _this.tName = '';
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
            updateTemplate: function () {
                var _this = this;
                $UU.http.put("/template/update",
                    _this.templateForm
                    , function (response) {
                        //获取回调数据
                        console.log(response.data);
                        if (response.data.code === 0) {
                            _this.$message.success(response.data.msg);
                            _this.dialogFormVisible = false;
                            _this.fetchFilesBySetId(_this.curId);
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
            getTemplateById: function (id) {
                var _this = this;
                //请求参数
                var req = {};
                $UU.http.get("/template/get-by-id/" + id,
                    req
                    , function (response) {
                        //获取回调数据
                        console.log(response.data);
                        if (response.data.code === 0) {
                            _this.templateForm = response.data.data;
                            if(!!_this.templateForm.content){
                                _this.codemirror?.setValue(_this.templateForm.content);
                            }else{
                                _this.codemirror?.setValue("");
                            }
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
            editCurTemplate:function(row){
                let _this  = this;
                _this.getTemplateById(row.id)
            },
            onCmReady(cm) {
                console.log('the editor is readied!', cm)
                let _this = this;
                this.$refs.myCm.codemirror.setSize("100%", "500px");
                this.codemirror = this.$refs.myCm.codemirror;
                if(!!_this.templateForm.content){
                    _this.codemirror?.setValue(_this.templateForm.content);
                }else{
                    _this.codemirror?.setValue("");
                }
                //keyup 自动提示
                _this.codemirror.on('keyup', function (cm, event) {
                    if (!cm.state.completionActive &&   /*Enables keyboard navigation in autocomplete list*/
                        event.keyCode > 64 && event.keyCode < 91) {// only when a letter key is pressed
                        CodeMirror.commands.autocomplete(cm, null, {completeSingle: false});
                    }
                });
            },
            onCmFocus(cm) {
                // console.log('the editor is focus!', cm)
            },
            onCmCodeChange(newCode) {
                // console.log('this is new code', newCode);
            },
            submitTemplate:function(){
                let _this = this;
                if(!!!_this.templateForm.content){
                    _this.$message.warning("文件类容不能为空");
                    return;
                }
                _this.updateTemplate();
            }

        }
    });


