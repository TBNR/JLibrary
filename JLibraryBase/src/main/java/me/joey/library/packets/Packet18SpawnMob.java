package me.joey.library.packets;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.comphenix.protocol.injector.PacketConstructor;
import com.comphenix.protocol.wrappers.WrappedDataWatcher;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class Packet18SpawnMob extends AbstractPacket {
    public static final int ID = 24;

    private static PacketConstructor entityConstructor;

    public Packet18SpawnMob() {
        super(new PacketContainer(ID), ID);
        handle.getModifier().writeDefaults();
    }

    @SuppressWarnings("unused")
    public Packet18SpawnMob(PacketContainer packet) {
        super(packet, ID);
    }

    @SuppressWarnings("unused")
    public Packet18SpawnMob(Entity entity) {
        super(fromEntity(entity), ID);
    }

    // Useful constructor
    private static PacketContainer fromEntity(Entity entity) {
        if (entityConstructor == null) {
            entityConstructor = ProtocolLibrary.getProtocolManager().createPacketConstructor(ID, entity);
        }
        return entityConstructor.createPacket(entity);
    }

    /**
     * Retrieve entity ID.
     *
     * @return The current EID
     */
    @SuppressWarnings("unused")
    public int getEntityID() {
        return handle.getIntegers().read(0);
    }

    /**
     * Retrieve the entity that will be spawned.
     *
     * @param world - the current world of the entity.
     * @return The spawned entity.
     */
    public Entity getEntity(World world) {
        return handle.getEntityModifier(world).read(0);
    }

    /**
     * Retrieve the entity that will be spawned.
     *
     * @param event - the packet event.
     * @return The spawned entity.
     */
    @SuppressWarnings("unused")
    public Entity getEntity(PacketEvent event) {
        return getEntity(event.getPlayer().getWorld());
    }

    /**
     * Set entity ID.
     *
     * @param value - new value.
     */
    public void setEntityID(int value) {
        handle.getIntegers().write(0, value);
    }

    /**
     * Retrieve the type of mob.
     *
     * @return The current Type
     */
    public EntityType getType() {
        return EntityType.fromId(handle.getIntegers().read(1));
    }

    /**
     * Set the type of mob.
     *
     * @param value - new value.
     */
    public void setType(EntityType value) {
        handle.getIntegers().write(1, (int) value.getTypeId());
    }

    /**
     * Retrieve the x position of the object.
     * <p/>
     * Note that the coordinate is rounded off to the nearest 1/32 of a meter.
     *
     * @return The current X
     */
    @SuppressWarnings("unused")
    public double getX() {
        return handle.getIntegers().read(2) / 32.0D;
    }

    /**
     * Set the x position of the object.
     *
     * @param value - new value.
     */
    public void setX(double value) {
        handle.getIntegers().write(2, (int) Math.floor(value * 32.0D));
    }

    /**
     * Retrieve the y position of the object.
     * <p/>
     * Note that the coordinate is rounded off to the nearest 1/32 of a meter.
     *
     * @return The current y
     */
    @SuppressWarnings("unused")
    public double getY() {
        return handle.getIntegers().read(3) / 32.0D;
    }

    /**
     * Set the y position of the object.
     *
     * @param value - new value.
     */
    public void setY(double value) {
        handle.getIntegers().write(3, (int) Math.floor(value * 32.0D));
    }

    /**
     * Retrieve the z position of the object.
     * <p/>
     * Note that the coordinate is rounded off to the nearest 1/32 of a meter.
     *
     * @return The current z
     */
    @SuppressWarnings("unused")
    public double getZ() {
        return handle.getIntegers().read(4) / 32.0D;
    }

    /**
     * Set the z position of the object.
     *
     * @param value - new value.
     */
    public void setZ(double value) {
        handle.getIntegers().write(4, (int) Math.floor(value * 32.0D));
    }

    /**
     * Retrieve the yaw.
     *
     * @return The current Yaw
     */
    @SuppressWarnings("unused")
    public float getYaw() {
        return (handle.getBytes().read(0) * 360.F) / 256.0F;
    }

    /**
     * Set the yaw of the spawned mob.
     *
     * @param value - new yaw.
     */
    @SuppressWarnings("unused")
    public void setYaw(float value) {
        handle.getBytes().write(0, (byte) (value * 256.0F / 360.0F));
    }

    /**
     * Retrieve the pitch.
     *
     * @return The current pitch
     */
    @SuppressWarnings("unused")
    public float getPitch() {
        return (handle.getBytes().read(1) * 360.F) / 256.0F;
    }

    /**
     * Set the pitch of the spawned mob.
     *
     * @param value - new pitch.
     */
    @SuppressWarnings("unused")
    public void setPitch(float value) {
        handle.getBytes().write(1, (byte) (value * 256.0F / 360.0F));
    }

    /**
     * Retrieve the yaw of the mob's head.
     *
     * @return The current yaw.
     */
    @SuppressWarnings("unused")
    public float getHeadYaw() {
        return (handle.getBytes().read(2) * 360.F) / 256.0F;
    }

    /**
     * Set the yaw of the mob's head.
     *
     * @param value - new yaw.
     */
    @SuppressWarnings("unused")
    public void setHeadYaw(float value) {
        handle.getBytes().write(2, (byte) (value * 256.0F / 360.0F));
    }

    /**
     * Retrieve the velocity in the x axis.
     *
     * @return The current velocity X
     */
    @SuppressWarnings("unused")
    public double getVelocityX() {
        return handle.getIntegers().read(5) / 8000.0D;
    }

    /**
     * Set the velocity in the x axis.
     *
     * @param value - new value.
     */
    @SuppressWarnings("unused")
    public void setVelocityX(double value) {
        handle.getIntegers().write(5, (int) (value * 8000.0D));
    }

    /**
     * Retrieve the velocity in the y axis.
     *
     * @return The current velocity y
     */
    @SuppressWarnings("unused")
    public double getVelocityY() {
        return handle.getIntegers().read(6) / 8000.0D;
    }

    /**
     * Set the velocity in the y axis.
     *
     * @param value - new value.
     */
    @SuppressWarnings("unused")
    public void setVelocityY(double value) {
        handle.getIntegers().write(6, (int) (value * 8000.0D));
    }

    /**
     * Retrieve the velocity in the z axis.
     *
     * @return The current velocity z
     */
    @SuppressWarnings("unused")
    public double getVelocityZ() {
        return handle.getIntegers().read(7) / 8000.0D;
    }

    /**
     * Set the velocity in the z axis.
     *
     * @param value - new value.
     */
    @SuppressWarnings("unused")
    public void setVelocityZ(double value) {
        handle.getIntegers().write(7, (int) (value * 8000.0D));
    }

    /**
     * Retrieve the data watcher.
     * <p/>
     * Content varies by mob, see Entities.
     *
     * @return The current Metadata
     */
    @SuppressWarnings("unused")
    public WrappedDataWatcher getMetadata() {
        return handle.getDataWatcherModifier().read(0);
    }

    /**
     * Set the data watcher.
     *
     * @param value - new value.
     */
    public void setMetadata(WrappedDataWatcher value) {
        handle.getDataWatcherModifier().write(0, value);
    }
}
