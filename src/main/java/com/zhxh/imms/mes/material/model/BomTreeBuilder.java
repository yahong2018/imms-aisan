package com.zhxh.imms.mes.material.model;

import com.zhxh.imms.mes.material.domain.Bom;
import com.zhxh.imms.mes.material.logic.BomLogic;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class BomTreeBuilder {
    private final BomLogic bomLogic;

    public BomTreeBuilder(BomLogic bomLogic) {
        this.bomLogic = bomLogic;
    }

    public void clearLeaf(List<BomTree> treeList) {
        for (int i = 0; i < treeList.size(); i++) {
            BomTree tree = treeList.get(i);
            if (tree.isLeaf()) {
                treeList.remove(i);
                i--;
                continue;
            }
            this.clearLeaf(tree.getChildren());
        }
    }

    public BomTree buildTree(String materialCode, boolean clearLeaf) {
        List<Bom> bomList = this.bomLogic.getMaterialBom(materialCode);
        boolean first = true;
        BomTree self = new BomTree();
        self.setChildren(new ArrayList<>());
        for (Bom bom : bomList) {
            if (first) {
                self.setBomId(-1L);
                self.setMaterialQty(1);
                self.setComponentQty(1);
                self.setComponentId(bom.getMaterialId());
                self.setComponentCode(bom.getMaterialCode());
                self.setComponentName(bom.getMaterialName());
                self.setLeaf(false);
                self.setExpanded(true);

                first = false;
            }

            BomTree treeBom = new BomTree();
            assignBom(bom, treeBom);
            self.getChildren().add(treeBom);

            this.fillTreeBom(treeBom);
        }
        if (clearLeaf) {
            this.clearLeaf(self.getChildren());
        }
        if (self.getChildren().size() == 0) {
            return null;
        }
        return self;
    }

    private void fillTreeBom(BomTree parent) {
        String materialCode = parent.getComponentCode();
        List<Bom> children = this.bomLogic.getMaterialBom(materialCode);
        int childrenCount = children.size();
        parent.setExpanded(childrenCount > 0);
        parent.setLeaf(childrenCount == 0);
        parent.setChildren(new ArrayList<>());
        for (Bom childBom : children) {
            BomTree childTree = new BomTree();
            assignBom(childBom, childTree);
            parent.getChildren().add(childTree);
            this.fillTreeBom(childTree);
        }
    }

    private void assignBom(Bom bom, BomTree treeBom) {
        treeBom.setBomId(bom.getRecordId());
        treeBom.setComponentId(bom.getComponentId());
        treeBom.setComponentCode(bom.getComponentCode());
        treeBom.setComponentName(bom.getComponentName());
        treeBom.setMaterialQty(bom.getMaterialQty());
        treeBom.setComponentQty(bom.getComponentQty());
        if (treeBom.getComponentQty() == 0) {
            treeBom.setComponentQty(1);
        }
    }
}
