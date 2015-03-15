/*
 * Copyright 2013-2015 must-be.org
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.mustbe.consulo.kruntime.module.extension;

import javax.swing.JComponent;

import org.consulo.module.extension.MutableModuleInheritableNamedPointer;
import org.consulo.module.extension.ui.ModuleExtensionSdkBoxBuilder;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.mustbe.consulo.RequiredDispatchThread;
import org.mustbe.consulo.dotnet.module.extension.DotNetSimpleMutableModuleExtension;
import com.intellij.openapi.projectRoots.Sdk;
import com.intellij.openapi.roots.ModuleRootLayer;
import com.intellij.util.NullableFunction;
import lombok.val;

/**
 * @author VISTALL
 * @since 22.02.2015
 */
public class KRuntimeMutableModuleExtension extends KRuntimeModuleExtension implements DotNetSimpleMutableModuleExtension<KRuntimeModuleExtension>
{
	public KRuntimeMutableModuleExtension(@NotNull String id, @NotNull ModuleRootLayer rootModel)
	{
		super(id, rootModel);
	}

	@NotNull
	@Override
	public MutableModuleInheritableNamedPointer<Sdk> getInheritableSdk()
	{
		return (MutableModuleInheritableNamedPointer<Sdk>) super.getInheritableSdk();
	}

	@Nullable
	@Override
	@RequiredDispatchThread
	public JComponent createConfigurablePanel(@NotNull Runnable runnable)
	{
		val sdkBoxBuilder = ModuleExtensionSdkBoxBuilder.<KRuntimeMutableModuleExtension>create(this, runnable);
		sdkBoxBuilder.sdkTypeClass(getSdkTypeClass());
		sdkBoxBuilder.sdkPointerFunc(new NullableFunction<KRuntimeMutableModuleExtension, MutableModuleInheritableNamedPointer<Sdk>>()
		{
			@Nullable
			@Override
			public MutableModuleInheritableNamedPointer<Sdk> fun(KRuntimeMutableModuleExtension mutableModuleExtension)
			{
				return mutableModuleExtension.getInheritableSdk();
			}
		});

		return wrapToNorth(sdkBoxBuilder.build());
	}

	@Override
	public void setEnabled(boolean b)
	{
		myIsEnabled = b;
	}

	@Override
	public boolean isModified(@NotNull KRuntimeModuleExtension kModuleExtension)
	{
		return isModifiedImpl(kModuleExtension);
	}
}
