package sample;

import com.sun.deploy.util.StringUtils;

public class GenerateService {

    // Sort info
    public static String generateSortInfo(String src) {
        String[] columns = src.split("\n");
        StringBuilder sb = new StringBuilder();

        sb.append("private Sort getSortInfo(@RequestParam(required = false) Map<String, String> filter) {\n");
        for (String line : columns) {
            String[] cols = line.split(";");
            String col = cols[0];
            sb.append("String " + col + " = filter.get(\"sort[" + col + "]\");\n");
        }

        sb.append("Sort sort = null;\n");

        boolean firstColumn = true;
        for (String line : columns) {
            String[] cols = line.split(";");
            String col = cols[0];
            if (!firstColumn) {
                sb.append("} else ");
            }

            sb.append("if (!StringUtils.isEmpty(" + col + ")) {");
            sb.append("if (\"asc\".equals(" + col + ")) {\n" +
                    "                sort = new Sort(Sort.Direction.ASC, \"" + col + "\");\n" +
                    "            } else {\n" +
                    "                sort = new Sort(Sort.Direction.DESC, \"" + col + "\");\n" +
                    "            }\n");

            if (firstColumn) {
                firstColumn = !firstColumn;
            }
        }
        sb.append("return sort;\n");
        sb.append("}");
        return sb.toString();
    }

    // Grid def
    public static String generateGridDef(String src) {
        String[] columns = src.split("\n");
        StringBuilder sb = new StringBuilder();

        sb.append("grida = webix.ui({\n" +
                "            container: \"grid\",\n" +
                "            view: \"datatable\",\n" +
                "            select: \"row\",\n" +
                "            width: (window.innerWidth * 0.98),\n" +
                "            height: (window.innerHeight * 0.72),\n" +
                "            datafetch: 20,\n" +
                "            dragColumn: true,\n" +
                "            columns: [");

        for (int i = 0; i<columns.length; i++) {
            String[] cols = columns[i].split(";");
            String id = cols[0];
            String value = "";
            String adjust = "";
            String format = "";

            if (cols != null) {
                if (cols.length > 1) {
                    value = cols[1];
                }

                if (cols.length > 2) {
                    adjust = cols[2];
                }

                if (cols.length > 3) {
                    format = cols[3];
                }
            }

            sb.append("{id: \"" + id + "\", sort: \"server\", header: \"" + value + "\", adjust: \"" + adjust + "\"");
            if (format != null && format.length() > 0) {
                sb.append(", format: " + format);
            }
            sb.append("}");

            if (i < columns.length - 1) {
                sb.append(",\n");
            }
        }
        sb.append("],\n" +
                "            scheme:{\n" +
                "                $init:function(obj){ obj.index = this.count(); }\n" +
                "            },\n" +
                "            autoConfig: true,\n" +
                "            pager: {\n" +
                "                container: \"paging_here\",\n" +
                "                template: '{common.first()} {common.prev()} {common.pages()} {common.next()} {common.last()} total: #count#',\n" +
                "                size: 20,\n" +
                "                group: 8\n" +
                "            },\n" +
                "            on: {\n" +
                "                onSelectChange: function () {\n" +
                "                    window.location.href = \"litige.html?litige_id=\" + grida.getSelectedId(true).join();\n" +
                "                }\n" +
                "            },\n" +
                "            url: \"http://\"+serverIP+\":9090/textma/litige?search=\" + document.getElementById(\"myfilter\").value\n" +
                "        });");

        return sb.toString();
    }

    // Side menu
    public static String generateSideMenu(String src) {
        String[] columns = src.split("\n");
        StringBuilder sb = new StringBuilder();

        sb.append("webix.ui({\n" +
                "        view: \"sidemenu\",\n" +
                "        id: \"showHideColumnsMenu\",\n" +
                "        width: 300,\n" +
                "        height: 100,\n" +
                "        position: \"right\",\n" +
                "        css: \"rightSideMenu\",\n" +
                "        body: {\n" +
                "            view: \"list\",\n" +
                "            borderless: true,\n" +
                "            scroll: false,\n" +
                "\n" +
                "            template: \"<button onclick=showOrHideColumn('#id#') class='btn button_raised col-md-12 bg_success'>#value#</button>\",\n" +
                "            data: [");

        for (int i = 0; i<columns.length; i++) {
            String[] cols = columns[i].split(";");
            String value = "";
            if (cols != null && cols.length > 1) {
                value = cols[1];
            }
            sb.append("{id: \"" + cols[0] + "\", value: \"" + value + "\"}");

            if (i < columns.length - 1) {
                sb.append(",\n");
            }
        }
        sb.append("],\n" +
                "            select: true,\n" +
                "            type: {\n" +
                "                height: 40\n" +
                "            }\n" +
                "        }\n" +
                "    });");

        return sb.toString();
    }
}
