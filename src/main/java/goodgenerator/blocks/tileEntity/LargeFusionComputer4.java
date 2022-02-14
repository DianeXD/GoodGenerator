package goodgenerator.blocks.tileEntity;

import com.github.technus.tectech.thing.metaTileEntity.hatch.GT_MetaTileEntity_Hatch_EnergyMulti;
import goodgenerator.blocks.tileEntity.base.LargeFusionComputer;
import goodgenerator.loader.Loaders;
import goodgenerator.util.DescTextLocalization;
import gregtech.api.enums.Materials;
import gregtech.api.enums.TAE;
import gregtech.api.enums.Textures;
import gregtech.api.interfaces.ITexture;
import gregtech.api.interfaces.metatileentity.IMetaTileEntity;
import gregtech.api.interfaces.tileentity.IGregTechTileEntity;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Energy;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Input;
import gregtech.api.metatileentity.implementations.GT_MetaTileEntity_Hatch_Output;
import gregtech.api.render.TextureFactory;
import gregtech.api.util.GT_Multiblock_Tooltip_Builder;
import gtPlusPlus.core.block.ModBlocks;
import gtPlusPlus.xmod.gregtech.common.blocks.textures.TexturesGtBlock;
import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import static goodgenerator.util.DescTextLocalization.BLUE_PRINT_INFO;
import static gregtech.api.enums.Textures.BlockIcons.MACHINE_CASING_FUSION_GLASS;

public class LargeFusionComputer4 extends LargeFusionComputer {

    public LargeFusionComputer4(int id, String name, String nameRegional) {
        super(id,name,nameRegional);
    }

    public LargeFusionComputer4(String name) {
        super(name);
    }

    @Override
    public String[] getDescription() {
        final GT_Multiblock_Tooltip_Builder tt = new GT_Multiblock_Tooltip_Builder();
        tt.addMachineType("Fusion Reactor")
                .addInfo("Galaxy Collapse.")
                .addInfo("Controller block for the Compact Fusion Reactor MK-IV Prototype.")
                .addInfo("4194304EU/t and 40M EU capacity per Energy Hatch")
                .addInfo("If the recipe has a startup cost greater than the")
                .addInfo("number of energy hatches * cap, you can't do it")
                .addInfo("Make sure the whole structure is built in the 3x3")
                .addInfo("chuck area of the ring center (not controller).")
                .addInfo("It can run 256x recipes at most.")
                .addInfo("Support" + EnumChatFormatting.BLUE + " Tec" + EnumChatFormatting.DARK_BLUE + "Tech" + EnumChatFormatting.GRAY + " Energy/Laser Hatches!")
                .addInfo("The structure is too complex!")
                .addInfo(BLUE_PRINT_INFO)
                .addSeparator()
                .addCasingInfo("Fusion Machine Casing MK III", 1668)
                .addCasingInfo("Compact Fusion Coil MK-II Prototype", 558)
                .addCasingInfo("Infinity Frame Box", 128)
                .addCasingInfo("Uranium Reinforced Borosilicate Glass Block", 63)
                .addEnergyHatch("1-32, Hint block with dot 3", 3)
                .addInputHatch("2-16, Hint block with dot 1", 1)
                .addOutputHatch("1-16, Hint block with dot 2", 2)
                .addStructureInfo("ALL Hatches must be UHV or better")
                .toolTipFinisher("Good Generator");
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            return tt.getInformation();
        } else {
            return tt.getStructureInformation();
        }
    }

    @Override
    public long maxEUStore() {
        return 1280060000L * (Math.min(32, this.mEnergyHatches.size() + this.eEnergyMulti.size())) / 32L;
    }

    @Override
    public Block getCasingBlock() {
        return ModBlocks.blockCasings3Misc;
    }

    @Override
    public int getCasingMeta() {
        return 12;
    }

    @Override
    public Block getCoilBlock() {
        return Loaders.compactFusionCoil;
    }

    @Override
    public int getCoilMeta() {
        return 3;
    }

    @Override
    public Block getGlassBlock() {
        return Loaders.newGlass;
    }

    @Override
    public int getGlassMeta() {
        return 0;
    }

    @Override
    public int hatchTier() {
        return 9;
    }

    @Override
    public Materials getFrameBox() {
        return Materials.Infinity;
    }

    @Override
    public ITexture getTextureOverlay() {
        if (this.mMaxProgresstime > 0)
            return TextureFactory.of(TextureFactory.builder().addIcon(TexturesGtBlock.Casing_Machine_Screen_3).extFacing().build());
        else
            return TextureFactory.of(TextureFactory.builder().addIcon(TexturesGtBlock.Casing_Machine_Screen_1).extFacing().build());
    }

    @Override
    public int tierOverclock() {
        return 8;
    }

    @Override
    public String[] getStructureDescription(ItemStack stackSize) {
        return DescTextLocalization.addText("LargeFusion4.hint", 9);
    }

    @Override
    public boolean turnCasingActive(boolean status) {
        if (this.mEnergyHatches != null) {
            for (GT_MetaTileEntity_Hatch_Energy hatch : this.mEnergyHatches) {
                hatch.updateTexture(status ? TAE.getIndexFromPage(2, 14) : 53);
            }
        }
        if (this.eEnergyMulti != null) {
            for (GT_MetaTileEntity_Hatch_EnergyMulti hatch : this.eEnergyMulti) {
                hatch.updateTexture(status ? TAE.getIndexFromPage(2, 14) : 53);
            }
        }
        if (this.mOutputHatches != null) {
            for (GT_MetaTileEntity_Hatch_Output hatch : this.mOutputHatches) {
                hatch.updateTexture(status ? TAE.getIndexFromPage(2, 14) : 53);
            }
        }
        if (this.mInputHatches != null) {
            for (GT_MetaTileEntity_Hatch_Input hatch : this.mInputHatches) {
                hatch.updateTexture(status ? TAE.getIndexFromPage(2, 14) : 53);
            }
        }
        return true;
    }

    @Override
    public ITexture[] getTexture(IGregTechTileEntity aBaseMetaTileEntity, byte aSide, byte aFacing, byte aColorIndex, boolean aActive, boolean aRedstone) {
        if (aSide == aFacing) return new ITexture[]{TextureFactory.builder().addIcon(MACHINE_CASING_FUSION_GLASS).extFacing().build(), getTextureOverlay()};
        if (!aActive) return new ITexture[]{Textures.BlockIcons.getCasingTextureForId(52)};
        return new ITexture[]{TextureFactory.builder().addIcon(TexturesGtBlock.TEXTURE_CASING_FUSION_CASING_ULTRA).extFacing().build()};
    }

    @Override
    public IMetaTileEntity newMetaEntity(IGregTechTileEntity aTileEntity) {
        return new LargeFusionComputer4(mName);
    }
}